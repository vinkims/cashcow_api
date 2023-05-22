package com.example.cashcow_api.breed;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    // @Test
    public void testGetBreeds() throws Exception {
        when(sBreed.getAll()).thenReturn(Collections.singletonList(breed));
        mockMvc.perform(get("/breed"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetBreedById() {
        // when(breedDAO.findById(1)).thenReturn(Optional.of(breed));
        // when(sBreed.create(any(BreedDTO.class))).thenReturn(breed);

        EBreed breed2 = new EBreed();
        breed2.setCreatedOn(LocalDateTime.now());
        breed2.setDescription("Produces lots of milk");
        breed2.setId(12);
        breed2.setName("Fresian");
        when(sBreed.getById(12)).thenReturn(Optional.of(breed2));
        // when(sBreed.create(any(BreedDTO.class))).thenReturn(breed);

        try {
            mockMvc.perform(get("/breed/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Fresian")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$").isNotEmpty());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateBreed() throws Exception {
        when(sBreed.create(any(BreedDTO.class))).thenReturn(breed);

        mockMvc.perform(post("/breed")
                .content(objectMapper.writeValueAsString(breedDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
        
    }
}
