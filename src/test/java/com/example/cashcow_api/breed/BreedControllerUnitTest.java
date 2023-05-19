package com.example.cashcow_api.breed;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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

import com.example.cashcow_api.controllers.CBreed;
import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.services.auth.SUserDetails;
import com.example.cashcow_api.services.cow.SBreed;
import com.example.cashcow_api.utils.JwtUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CBreed.class)
@AutoConfigureMockMvc(addFilters = false)
public class BreedControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SBreed sBreed;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SUserDetails sUserDetails;

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
    public void testGetBreeds() throws Exception {
        when(sBreed.getAll()).thenReturn(Collections.singletonList(breed));
        mockMvc.perform(get("/breed"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
