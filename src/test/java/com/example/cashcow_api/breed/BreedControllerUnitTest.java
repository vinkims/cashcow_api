package com.example.cashcow_api.breed;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.cashcow_api.controllers.CBreed;
import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.repositories.BreedDAO;
import com.example.cashcow_api.services.auth.SUserDetails;
import com.example.cashcow_api.services.cow.SBreed;
import com.example.cashcow_api.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CBreed.class)
@AutoConfigureMockMvc(addFilters = false)
public class BreedControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BreedDAO breedDAO;

    @MockBean
    SBreed sBreed;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SUserDetails sUserDetails;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private EBreed breed;

    private BreedDTO breedDTO;

    @BeforeEach
    public void setup() {
        breed = new EBreed();
        breed.setDescription("Produces lots of milk");
        breed.setId(1);
        breed.setName("Fresian");

        breedDTO = new BreedDTO();
        breedDTO.setDescription("Produces lots of milk");
        breedDTO.setName("Fresian");
    }

    @Test
    public void shouldCreateBreed() throws Exception {
        when(sBreed.create(any(BreedDTO.class))).thenReturn(breed);

        mockMvc.perform(post("/breed")
                .content(objectMapper.writeValueAsString(breedDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
        
    }

    @Test
    public void shouldReturnBreed() throws Exception {
        when(sBreed.getById(1, true)).thenReturn(breed);

        mockMvc.perform(get("/breed/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", is("Fresian")))
            .andExpect(jsonPath("$.content.id", is(1)))
            .andExpect(jsonPath("$").isNotEmpty()); 
    }

    @Test
    public void shouldReturnBreedNotFound() throws Exception {
        when(sBreed.getById(100, true)).thenThrow(
            new NotFoundException("breed with specified id not found", "breedId"));

        mockMvc.perform(get("/breed/100"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateBreed() throws Exception {
        EBreed newBreed = new EBreed();
        newBreed.setName("Jersey");
        newBreed.setId(1);
        newBreed.setDescription("Produces lots of milk");

        BreedDTO newBreedDTO = new BreedDTO();
        newBreedDTO.setName("Jersey");

        when(sBreed.getById(1, true)).thenReturn(breed);
        when(sBreed.update(1, newBreedDTO)).thenReturn(newBreed);

        mockMvc.perform(patch("/breed/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBreedDTO)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.name", is("Jersey")));
    }
}
