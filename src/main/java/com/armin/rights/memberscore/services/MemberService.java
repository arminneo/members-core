package com.armin.rights.memberscore.services;

import com.armin.rights.memberscore.errors.MemberNotFoundException;
import com.armin.rights.memberscore.models.Member;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.stores.MemberStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberStore store;
    private final FileStorageService fileStorageService;

    public MemberService(MemberStore store, FileStorageService fileStorageService) {
        this.store = store;
        this.fileStorageService = fileStorageService;
    }

    public List<MemberModels.Output> all() {
        return store.findAll()
                .stream()
                .map(MemberModels.Output::box)
                .collect(Collectors.toList());
    }

    public MemberModels.Output add(MemberModels.Input member) {
        return MemberModels.Output.box(store.save(member.unbox()));
    }

    public MemberModels.Output get(Long id) throws MemberNotFoundException {
        return MemberModels.Output.box(store.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id)));
    }

    public MemberModels.Output update(Long id, MemberModels.Input memberInput) throws MemberNotFoundException {
        Member member = memberInput.unbox();
        return store.findById(id)
                .map(m -> {
                    m.setFirstName(member.getFirstName());
                    m.setLastName(member.getLastName());
                    m.setBirthday(member.getBirthday());
                    m.setZipcode(member.getZipcode());
                    return MemberModels.Output.box(store.save(m));
                })
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public void delete(Long id) {
        Member member = store.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        if (!StringUtils.isEmpty(member.getPicture())) {
            try {
                fileStorageService.deleteFile(member.getPicture());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        store.deleteById(member.getId());
    }

    public void updatePicture(Long id, String pictureFile) throws MemberNotFoundException {

        store.findById(id)
                .map(m -> {
                    m.setPicture(pictureFile);
                    return MemberModels.Output.box(store.save(m));
                })
                .orElseThrow(() -> new MemberNotFoundException(id));
    }


}
