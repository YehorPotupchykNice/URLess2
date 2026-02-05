package org.example;

import org.example.dto.CreateURLCollectionRequest;
import org.example.dto.CreateURLCollectionResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private URLGateway urlGateway;
    @Autowired
    private URLCollectionGateway urlCollectionGateway;

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
        var expected = urlCollectionGateway.create(ids, "qwty2");

        var om = new ObjectMapper();
        var r = mockMvc.perform(get("/qwty2"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        var response = om.readValue(r, CreateURLCollectionResponse.class);
        var expectedUrls = expected.getUrls().stream().map(u -> "https://urle.ss/" + u.getId()).toList();

        assertEquals("https://urle.ss/" + expected.getId(), response.getUrl());
        assertTrue(response.getUrls().containsAll(expectedUrls));
    }
}
