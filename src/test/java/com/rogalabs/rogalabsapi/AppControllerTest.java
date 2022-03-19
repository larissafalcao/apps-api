package com.rogalabs.rogalabsapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogalabs.rogalabsapi.domain.AppEntity;
import com.rogalabs.rogalabsapi.repository.AppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AppControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    AppRepository appRepository;

    private AppEntity app;

    private AppEntity buildApp( String name, String owner, Double price) {
        return  AppEntity.builder()
                .name(name)
                .owner(owner)
                .price(price)
                .type("Entertainment")
                .build();
    }

    @BeforeEach
    public void initTest() {
        app = buildApp("Instagram", "Meta", 1.00);
    }

    @Test
    public void saveSuccess() throws Exception {
        mockMvc.perform(post("/api/app")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(app)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value(app.getName()))
                .andExpect(jsonPath("$.owner").value(app.getOwner()))
                .andExpect(jsonPath("$.price").value(app.getPrice()));
    }

    @Test
    public void shouldGetAllApps() throws Exception {

        this.mockMvc.perform(get("/api/app"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(5)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Instagram")))
                .andExpect(jsonPath("$.[*].owner").value(hasItem("Meta")))
                .andExpect(jsonPath("$.[*].price").value(hasItem(1.00)));
    }

    @Test
    public void shouldGetOneApp() throws Exception {

        this.mockMvc.perform(get("/api/app/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Instagram"))
                .andExpect(jsonPath("$.owner").value("Meta"))
                .andExpect(jsonPath("$.price").value(1.00))
                .andExpect(jsonPath("$.type").value("Entertainment"));
    }

    @Test
    public void getInexistentApp() throws Exception {
        this.mockMvc.perform(get("/api/app/100"))
                .andExpect(status().isNotFound());
    }



    @Test
    public void updateSuccess() throws Exception {

        this.mockMvc.perform(put( "/api/app/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value(app.getName()))
                .andExpect(jsonPath("$.owner").value(app.getOwner()))
                .andExpect(jsonPath("$.price").value(app.getPrice()));
    }

    @Test
    public void updateInexistentApp() throws Exception {
        this.mockMvc.perform(put( "/api/app/110")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(app)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetCheaperAppByType() throws Exception {
        String type = "Entertainment";

        this.mockMvc.perform(get("/api/app/getCheaperAppByType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("Instagram"))
                .andExpect(jsonPath("$.owner").value("Meta"))
                .andExpect(jsonPath("$.price").value(1.00))
                .andExpect(jsonPath("$.type").value("Entertainment"));
    }

    @Test
    public void shouldGetAppsByType() throws Exception {
        String type = "Entertainment";

        this.mockMvc.perform(get("/api/app/findByType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(type))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(5)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Instagram")))
                .andExpect(jsonPath("$.[*].owner").value(hasItem("Meta")))
                .andExpect(jsonPath("$.[*].price").value(hasItem(1.00)))
                .andExpect(jsonPath("$.[*].type").value(hasItem("Entertainment")));
    }

    @Test
    public void shouldGetAppsByName() throws Exception {
        String name = "Instagram";

        this.mockMvc.perform(get("/api/app/findByName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(5)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Instagram")))
                .andExpect(jsonPath("$.[*].owner").value(hasItem("Meta")))
                .andExpect(jsonPath("$.[*].price").value(hasItem(1.00)))
                .andExpect(jsonPath("$.[*].type").value(hasItem("Entertainment")));
    }
}
