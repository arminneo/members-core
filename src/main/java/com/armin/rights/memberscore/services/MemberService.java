package com.armin.rights.memberscore.services;

import com.armin.rights.memberscore.errors.MemberNotFoundException;
import com.armin.rights.memberscore.models.Member;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.stores.MemberStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberService {
    private final MemberStore store;

    public MemberService(MemberStore store) {
        this.store = store;
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
                    return MemberModels.Output.box(store.save(member));
                })
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public void delete(Long id) {
        store.deleteById(id);
    }
}
