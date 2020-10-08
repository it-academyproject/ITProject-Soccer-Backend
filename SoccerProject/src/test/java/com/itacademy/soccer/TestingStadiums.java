package com.itacademy.soccer;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itacademy.soccer.controller.json.StadiumJson;
import com.itacademy.soccer.dto.Stadium;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
public class TestingStadiums {

    @Autowired
    private MockMvc mockMvc;

    //inject context
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before("/id")
    public void setup() {
        //build mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllStadiumTest() throws Exception {
        this.mockMvc.perform(get("/api/stadiums")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }
    @Test
    public void getStadiumTest() throws Exception {
        //works as expected
        this.mockMvc.perform(get("/api/stadiums/{id}", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists());
                //.andExpect(jsonPath("$.success", is(true)));

    }
//    @Test
//    public void postStadiumTest() throws Exception {
//        mockMvc.perform( MockMvcRequestBuilders
//                .post("/api/stadiums")
//                .content(asJsonString(new Stadium( "testName", "testCity", 0, 0)))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.success").exists());
//    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void updateStadiumTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/api/stadiums")
                .content(asJsonString(new Stadium( "testName", "testCity", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").exists());
    }
    @Test
    public void deleteStadiumTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/api/stadiums")
                .content(asJsonString(new StadiumJson( "1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").exists());
    }
}
