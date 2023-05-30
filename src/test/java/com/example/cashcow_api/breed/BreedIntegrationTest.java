package com.example.cashcow_api.breed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.models.EContact;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.BreedDAO;
import com.example.cashcow_api.repositories.ContactDAO;
import com.example.cashcow_api.repositories.UserDAO;
import com.example.cashcow_api.services.contact.SContactType;
import com.example.cashcow_api.services.cow.SBreed;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BreedIntegrationTest {
    
    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;

    @Autowired
    private BreedDAO breedDAO;

    @Autowired
    private SBreed sBreed;

    @Autowired
    private SContactType sContactType;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .build();
        setupUser();
    }

    @AfterAll
    public void destroy() {
        breedDAO.deleteAll();
    }

    private void setupUser() {
        EUser user = new EUser();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPasscode("testP@ss123");
        userDAO.save(user);

        EContact contact = new EContact();
        contact.setValue("testuser@cashcow.com");
        contact.setContactType(sContactType.getById(1).get());
        contact.setUser(user);
        contactDAO.save(contact);

        List<EContact> contacts = new ArrayList<>();
        contacts.add(contact);

        user.setContacts(contacts);
    }

    @Test
    @WithUserDetails("testuser@cashcow.com")
    public void testCreateBreed() throws Exception {

        BreedDTO breedDTO = new BreedDTO();
        breedDTO.setDescription("Good for marginal areas");
        breedDTO.setName("Sahiwal");
        breedDTO.setStatusId(1);

        mockMvc.perform(post("/breed")
                .content(objectMapper.writeValueAsString(breedDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails("testuser@cashcow.com")
    public void testCreateBreed_Invalid_Input() throws Exception {

        BreedDTO breedDTO = new BreedDTO();
        breedDTO.setDescription("Good for marginal areas");
        breedDTO.setName("Sahiwal");
        breedDTO.setStatusId(1);

        mockMvc.perform(post("/breed")
                .content(objectMapper.writeValueAsString(breedDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("testuser@cashcow.com")
    public void getBreeds() throws Exception {
        mockMvc.perform(get("/breed"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andDo(print());
    }
}
