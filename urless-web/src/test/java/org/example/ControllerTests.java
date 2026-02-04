package org.example;

import org.example.dto.CreateURLCollectionRequest;
import org.example.dto.CreateURLCollectionResponse;
import org.example.dto.CreateURLRequest;
import org.example.dto.CreateURLResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shortener.URLCollectionGateway;
import shortener.URLGateway;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private URLGateway urlGateway;
    @Autowired
    private URLCollectionGateway urlCollectionGateway;

    @Test
    void test404OnNonexistent() throws Exception {
        mockMvc.perform(get("/NON-EXISTANT"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("URL does not exist"));
    }

    @Test
    void test301OnExisting() throws Exception {
        urlGateway.create("https://example.com", "qwery2");
        mockMvc.perform(get("/qwery2"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", "https://example.com"));
    }

    @Test
    void test201OnCreate() throws Exception {
        var request = new CreateURLRequest("https://example.com");
        var om = new ObjectMapper();
        var json = om.writeValueAsString(request);

        var r = mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        var response = om.readValue(r, CreateURLResponse.class);

        var expected = urlGateway.getAll().get(0);
        assertEquals(expected.getUrl(), response.getOriginalUrl());
        assertEquals("https://urle.ss/" + expected.getId(), response.getUrl());
    }

    @Test
    void test201OnCreateCollection() throws Exception {
        var urls = Arrays.asList("https://example.com/1", "https://example.com/2");
        var request = new CreateURLCollectionRequest(urls);
        var om = new ObjectMapper();
        var json = om.writeValueAsString(request);

        var r = mockMvc.perform(post("/collections").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        var response = om.readValue(r, CreateURLCollectionResponse.class);

        var expected = urlCollectionGateway.getAll().get(0);
        assertEquals("https://urle.ss/" + expected.getId(), response.getUrl());
    }

    @Test
    void test200OnExistingCollection() throws Exception {
        urlGateway.create("https://example.com/42", "xcvbn2");
        urlGateway.create("https://example.com/420", "xc5bn2");
        var ids = Arrays.asList("xcvbn2", "xc5bn2");
        urlCollectionGateway.create(ids, "qwty2");

        var om = new ObjectMapper();
        var r = mockMvc.perform(get("/qwty2"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = om.readValue(r, CreateURLCollectionResponse.class);
        var expected = urlCollectionGateway.getAll().get(0);
        var expectedUrls = expected.getUrls().stream().map(u -> "https://urle.ss/" + u.getId()).toList();

        assertEquals("https://urle.ss/" + expected.getId(), response.getUrl());
        assertTrue(response.getUrls().containsAll(expectedUrls));
    }
}

