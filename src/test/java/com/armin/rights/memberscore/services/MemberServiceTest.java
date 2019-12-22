package com.armin.rights.memberscore.services;

import com.armin.rights.memberscore.models.Member;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.stores.MemberStore;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {
    @Autowired
    MemberStore store;

    MemberService service;

    @BeforeEach
    void setUp() {
        Member m = new Member("FirstMember", "Last", new Date(), "12345");
        store.save(m);

        service = new MemberService(store);
    }

    @Test
    void all() {
        Assert.assertTrue(service.all().size() > 0);
    }

    @Test
    void add() {
        MemberModels.Input input = new MemberModels.Input();
        MemberModels.Output result = service.add(input);
        Assert.assertNotNull(result.getId());
    }

    @Test
    void get() {
        Member m = new Member("AddedMember", "LastMember", new Date(), "11111");
        m = store.save(m);
        MemberModels.Output result = service.get(m.getId());
        Assert.assertEquals(m.getFirstName(), result.getFirstName());
        Assert.assertEquals(m.getId(), result.getId());
    }

    @Test
    void update() {
        Member m = new Member("UpdateMember", "LastMember", new Date(), "22222");
        m = store.save(m);
        MemberModels.Input input = new MemberModels.Input("updatedNewName", "changed", m.getBirthday(), "33333");
        MemberModels.Output result = service.update(m.getId(), input);

        Assert.assertEquals(input.getZipcode(), result.getZipcode());
        Assert.assertEquals(input.getFirstName(), result.getFirstName());
        Assert.assertEquals(input.getLastName(), result.getLastName());
    }

    @Test
    void delete() {
        Member m = new Member("ToDelete", "RemoveThis", new Date(), "44444");
        m = store.save(m);
        service.delete(m.getId());

        Assert.assertFalse(store.existsById(m.getId()));
    }
}