package com.example.cashcow_api.contactType;

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

import com.example.cashcow_api.controllers.CContactType;
import com.example.cashcow_api.dtos.contact.ContactTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.repositories.ContactTypeDAO;
import com.example.cashcow_api.services.auth.SUserDetails;
import com.example.cashcow_api.services.contact.SContactType;
import com.example.cashcow_api.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CContactType.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactTypeControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ContactTypeDAO contactTypeDAO;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SContactType sContactType;

    @MockBean
    SUserDetails sUserDetails;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private EContactType contactType;

    private ContactTypeDTO contactTypeDTO;

    @BeforeEach
    public void setup() {
        contactType = new EContactType();
        contactType.setDescription("mobile number");
        contactType.setId(1);
        contactType.setName("mobile");

        contactTypeDTO = new ContactTypeDTO();
        contactTypeDTO.setDescription("mobile number");
        contactTypeDTO.setName("mobile");
    }

    @Test
    public void shouldCreateContactType() throws Exception {
        when(sContactType.create(any(ContactTypeDTO.class))).thenReturn(contactType);

        mockMvc.perform(post("/contact/type")
                .content(objectMapper.writeValueAsString(contactTypeDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnContactType() throws Exception {
        when(sContactType.getById(1, true)).thenReturn(contactType);

        mockMvc.perform(get("/contact/type/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", is("mobile")))
            .andExpect(jsonPath("$.content.id", is(1)))
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void shouldReturnContactTypeNotFound() throws Exception {
        when(sContactType.getById(100, true)).thenThrow(
            new NotFoundException("contact type with specified id not found", "contactTypeId")
        );

        mockMvc.perform(get("/contact/type/1"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateContactType() throws Exception {
        EContactType newContactType = new EContactType();
        newContactType.setDescription("email address");
        newContactType.setId(1);
        newContactType.setName("email");

        ContactTypeDTO newContactTypeDTO = new ContactTypeDTO();
        newContactTypeDTO.setDescription("email address");
        newContactTypeDTO.setName("email");

        when(sContactType.getById(1, true)).thenReturn(contactType);
        when(sContactType.update(1, newContactTypeDTO)).thenReturn(newContactType);

        mockMvc.perform(patch("/contact/type/1")
                .content(objectMapper.writeValueAsString(newContactTypeDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.name", is("email")));
    }
}
