package com.armin.rights.memberscore.controllers;

import com.armin.rights.memberscore.errors.MemberNotFoundException;
import com.armin.rights.memberscore.models.Member;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.services.MemberService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Members API")
@RestController
@RequestMapping(value = "/api/v1/members", produces = {
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
public class MembersController {
    private final MemberService service;

    public MembersController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    List<MemberModels.Output> all() {
        return service.all();
    }

    @PostMapping
    MemberModels.Output newMember(@RequestBody @Validated MemberModels.Input newMember) {
        return service.add(newMember);
    }

    @GetMapping("/{id}")
    MemberModels.Output one(@PathVariable Long id) throws MemberNotFoundException {
        return service.get(id);

    }

    @PutMapping("/{id}")
    MemberModels.Output updateMember(@RequestBody @Validated MemberModels.Input newMember, @PathVariable Long id) throws MemberNotFoundException {
        return service.update(id, newMember);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteMember(@PathVariable Long id) {
        service.delete(id);
    }
}
