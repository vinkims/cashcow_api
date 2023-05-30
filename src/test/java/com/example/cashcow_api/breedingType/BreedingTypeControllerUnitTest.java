package com.example.cashcow_api.breedingType;

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

import com.example.cashcow_api.controllers.CBreedingType;
import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.repositories.BreedingTypeDAO;
import com.example.cashcow_api.services.auth.SUserDetails;
import com.example.cashcow_api.services.breeding.SBreedingType;
import com.example.cashcow_api.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CBreedingType.class)
@AutoConfigureMockMvc(addFilters = false)
public class BreedingTypeControllerUnitTest {
 
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BreedingTypeDAO breedingTypeDAO;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SBreedingType sBreedingType;

    @MockBean
    SUserDetails sUserDetails;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private EBreedingType breedingType;

    private BreedingTypeDTO breedingTypeDTO;

    @BeforeEach
    public void setup() {
        breedingType = new EBreedingType();
        breedingType.setDescription("Natural insemination with a bull");
        breedingType.setId(1);
        breedingType.setName("Natural Breeding");

        breedingTypeDTO = new BreedingTypeDTO();
        breedingTypeDTO.setDescription("Natural insemination with a bull");
        breedingTypeDTO.setName("Natural Breeding");
    }

    @Test
    public void shouldCreateBreedingType() throws Exception {
        when(sBreedingType.create(any(BreedingTypeDTO.class))).thenReturn(breedingType);

        mockMvc.perform(post("/breeding/type")
                .content(objectMapper.writeValueAsString(breedingTypeDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBreedingType() throws Exception {
        when(sBreedingType.getById(1, true)).thenReturn(breedingType);

        mockMvc.perform(get("/breeding/type/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", is("Natural Breeding")))
            .andExpect(jsonPath("$.content.id", is(1)))
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void shouldReturnBreedingTypeNotFound() throws Exception {
        when(sBreedingType.getById(100, true)).thenThrow(
            new NotFoundException("breeding type with specified id not found", "breedingTypeId")
        );

        mockMvc.perform(get("/breeding/type/100"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateBreedingType() throws Exception {
        EBreedingType newBreedingType = new EBreedingType();
        newBreedingType.setDescription("Mating animals of different breeds");
        newBreedingType.setId(1);
        newBreedingType.setName("Cross Breeding");

        BreedingTypeDTO newBreedingTypeDTO = new BreedingTypeDTO();
        newBreedingTypeDTO.setName("Cross Breeding");
        newBreedingTypeDTO.setDescription("Mating unrelated animals");

        when(sBreedingType.getById(1, true)).thenReturn(breedingType);
        when(sBreedingType.update(1, newBreedingTypeDTO)).thenReturn(newBreedingType);

        mockMvc.perform(patch("/breeding/type/1")
                .content(objectMapper.writeValueAsString(newBreedingTypeDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.name", is("Cross Breeding")));
    }
}
