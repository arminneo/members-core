package com.armin.rights.memberscore.controllers;

import com.armin.rights.memberscore.models.Member;
import com.armin.rights.memberscore.models.MemberModels;
import com.armin.rights.memberscore.services.FileStorageService;
import com.armin.rights.memberscore.services.MemberService;
import com.armin.rights.memberscore.stores.MemberStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.armin.rights.memberscore.controllers.MembersController.MEMBER_PREFIX;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MembersControllerTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MemberStore store;

    MemberService service;

    @Autowired
    FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        Member m = new Member("FirstMember", "Last", new Date(), "12345");
        store.save(m);
        service = new MemberService(store, fileStorageService);
    }

    @Test
    void all() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(MEMBER_PREFIX + "/")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        String jsonBody = mvcResult.getResponse().getContentAsString();
        MemberModels.Output[] result = mapper.readValue(jsonBody, MemberModels.Output[].class);
        Assert.assertTrue(result.length > 0);
    }

    @Test
    void newMember() throws Exception {
        MemberModels.Input input = new MemberModels.Input("testuser", "testlast", new Date(), "12345");
        String jsonBody = mapper.writeValueAsString(input);

        MvcResult mvcResult = mockMvc.perform(post(MEMBER_PREFIX + "/")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        jsonBody = mvcResult.getResponse().getContentAsString();
        MemberModels.Output result = mapper.readValue(jsonBody, MemberModels.Output.class);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(result.getFirstName(), input.getFirstName());
        Assert.assertEquals(result.getZipcode(), input.getZipcode());
    }

    @Test
    void one() throws Exception {
        Member member = new Member("FirstMember", "Last", new Date(), "12345");
        store.save(member);

        MvcResult mvcResult = mockMvc.perform(get(MEMBER_PREFIX + "/" + member.getId().toString())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = mvcResult.getResponse().getContentAsString();
        MemberModels.Output result = mapper.readValue(jsonBody, MemberModels.Output.class);
        Assert.assertEquals(result.getId(), member.getId());
        Assert.assertEquals(result.getZipcode(), member.getZipcode());
    }

    @Test
    void validation() throws Exception {
        MemberModels.Input input = new MemberModels.Input("testuser", "testlast", new Date(), "ased");
        String jsonBody = mapper.writeValueAsString(input);

        mockMvc.perform(post(MEMBER_PREFIX + "/")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        jsonBody  = "{\"wrong\":\"json\"}";
        mockMvc.perform(post(MEMBER_PREFIX + "/")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void updateMember() throws Exception {
        Member member = new Member("FirstMember", "Last", new Date(), "12345");
        member = store.save(member);
        MemberModels.Input input = new MemberModels.Input("updated", "user", new Date(), "98765");
        String jsonBody = mapper.writeValueAsString(input);

        MvcResult mvcResult = mockMvc.perform(put(MEMBER_PREFIX + "/" + member.getId().toString())
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        jsonBody = mvcResult.getResponse().getContentAsString();
        MemberModels.Output result = mapper.readValue(jsonBody, MemberModels.Output.class);
        Assert.assertEquals(result.getFirstName(), input.getFirstName());
        Assert.assertEquals(result.getZipcode(), input.getZipcode());
    }

    @Test
    void deleteMember() throws Exception {
        Member member = new Member("FirstMember", "Last", new Date(), "12345");
        member = store.save(member);

        mockMvc.perform(delete(MEMBER_PREFIX + "/" + member.getId().toString())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void xml() throws Exception {
        com.fasterxml.jackson.dataformat.xml.XmlMapper xmlMapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();

        MemberModels.Input input = new MemberModels.Input("testuser", "testlast", new Date(), "12345");
        String xmlBody = xmlMapper.writeValueAsString(input);

        MvcResult mvcResult = mockMvc.perform(post(MEMBER_PREFIX + "/")
                .content(xmlBody)
                .contentType(MediaType.APPLICATION_XML)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        xmlBody = mvcResult.getResponse().getContentAsString();
        MemberModels.Output result = xmlMapper.readValue(xmlBody, MemberModels.Output.class);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(result.getFirstName(), input.getFirstName());
        Assert.assertEquals(result.getZipcode(), input.getZipcode());

    }

}