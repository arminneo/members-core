package com.armin.rights.memberscore.controllers;

import com.armin.rights.memberscore.errors.MemberNotFoundException;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.models.UploadFileResponse;
import com.armin.rights.memberscore.services.FileStorageService;
import com.armin.rights.memberscore.services.MemberService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Members API")
@RestController
@RequestMapping(value = MembersController.MEMBER_PREFIX, produces = {
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
public class MembersController {
    public static final String MEMBER_PREFIX = "/api/v1/members";
    private static final Logger logger = LoggerFactory.getLogger(MembersController.class);

    private final MemberService memberService;
    private final FileStorageService fileStorageService;

    public MembersController(MemberService memberService, FileStorageService fileStorageService) {
        this.memberService = memberService;
        this.fileStorageService = fileStorageService;
    }


    @GetMapping
    List<MemberModels.Output> all() {
        return memberService.all();
    }

    @PostMapping
    MemberModels.Output newMember(@RequestBody @Validated MemberModels.Input newMember) {
        return memberService.add(newMember);
    }

    @GetMapping("/{id}")
    MemberModels.Output one(@PathVariable Long id) throws MemberNotFoundException {
        return memberService.get(id);

    }

    @PutMapping("/{id}")
    MemberModels.Output updateMember(@RequestBody @Validated MemberModels.Input newMember, @PathVariable Long id) throws MemberNotFoundException {
        return memberService.update(id, newMember);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteMember(@PathVariable Long id) {
        memberService.delete(id);
    }

    @PostMapping("/uploadPicture/{id}")
    public UploadFileResponse uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        MemberModels.Output member = memberService.get(id);

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = getFullPictureUrl(fileName);

        memberService.updatePicture(id, fileName);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadPicture/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> result = new HashMap<>();
        result.put("date", (new Date()).toString());
        result.put("uploadDir", fileStorageService.getUploadDir());
        return result;
    }

    public static String getPictureBase(){
        return MEMBER_PREFIX + "/downloadPicture/";
    }

    public static String getFullPictureUrl(String fileName) {
        if (StringUtils.isEmpty(fileName)) return "";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(getPictureBase())
                .path(fileName)
                .toUriString();
    }
}
