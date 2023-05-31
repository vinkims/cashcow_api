package com.example.cashcow_api.cow;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.cashcow_api.controllers.CCow;
import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.repositories.CowDAO;
import com.example.cashcow_api.services.auth.SUserDetails;
import com.example.cashcow_api.services.cow.SCow;
import com.example.cashcow_api.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CCow.class)
@AutoConfigureMockMvc(addFilters = false)
public class CowControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CowDAO cowDAO;

    @MockBean
    SCow sCow;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SUserDetails sUserDetails;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private ECow cow;

    private CowDTO cowDTO;

    @BeforeEach
    public void setup() {
        cow = new ECow();
        cow.setColor("white");
        cow.setCreatedOn(LocalDateTime.now());
        cow.setGender("Female");
        cow.setId(1);
        cow.setName("Tumaini");
        
        cowDTO = new CowDTO();
        cowDTO.setColor("white");
        cowDTO.setGender("Female");
        cowDTO.setName("Tumaini");
    }

    @Test
    public void shouldCreateCow() throws Exception {
        when(sCow.create(any(CowDTO.class))).thenReturn(cow);

        mockMvc.perform(post("/cow")
                .content(objectMapper.writeValueAsString(cowDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnCow() throws Exception {
        when(sCow.getById(1, true)).thenReturn(cow);

        mockMvc.perform(get("/cow/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", is("Tumaini")))
            .andExpect(jsonPath("$.content.id", is(1)))
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void shouldReturnCowNotFound() throws Exception {
        when(sCow.getById(10, true)).thenThrow(
            new NotFoundException("cow with specified id not found", "cowId")
        );

        mockMvc.perform(get("/cow/10"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}
