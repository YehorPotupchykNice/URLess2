package org.example;

import org.example.dto.CreateURLRequest;
import org.example.dto.CreateURLResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shortener.URLGateway;
import tools.jackson.databind.ObjectMapper;

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

    @Test
    void test404OnNonexistent() throws Exception {
        mockMvc.perform(get("/NON-EXISTANT"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("URL does not exist"));
    }

    @Test
    void test301OnExisting() throws Exception {
        urlGateway.create("https://example.com", "qwerty2");
        mockMvc.perform(get("/qwerty2"))
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
}

