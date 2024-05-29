package com.ingryd.hms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingryd.hms.dto.HttpResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@Configuration
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testLogout() throws Exception {
        MvcResult result = mvc.perform(post("/auth/logout"))
                .andExpect(status().isOk())
                .andReturn();

        HttpResponseDto response = mapper.readValue(result.getResponse().getContentAsString(), HttpResponseDto.class);
        assertEquals("Logout successful", response.getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("AuthController", response.getEntity());
    }
}