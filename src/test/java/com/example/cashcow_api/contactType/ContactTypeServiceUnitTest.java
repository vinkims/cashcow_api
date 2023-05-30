package com.example.cashcow_api.contactType;

import com.example.cashcow_api.exceptions.NotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cashcow_api.dtos.contact.ContactTypeDTO;
import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.repositories.ContactTypeDAO;
import com.example.cashcow_api.services.contact.SContactType;

@ExtendWith(MockitoExtension.class)
public class ContactTypeServiceUnitTest {
    
    @Mock
    private ContactTypeDAO contactTypeDAO;

    @InjectMocks
    private SContactType sContactType;

    @Test
    public void testCreateContactType() {
        ContactTypeDTO contactTypeDTO = new ContactTypeDTO();
        contactTypeDTO.setDescription("email address");
        contactTypeDTO.setName("email");

        EContactType contactType = sContactType.create(contactTypeDTO);
        verify(contactTypeDAO, times(1)).save(contactType);

        ArgumentCaptor<EContactType> contactTypeArgCaptor = ArgumentCaptor.forClass(EContactType.class);
        verify(contactTypeDAO).save(contactTypeArgCaptor.capture());

        EContactType contactTypeCreated = contactTypeArgCaptor.getValue();
        assertNotNull(contactTypeCreated.getDescription());
        assertEquals("email", contactTypeCreated.getName());
    }

    @Test
    public void testGetContactTypeList() {
        EContactType mobile = new EContactType();
        mobile.setDescription("mobile number");
        mobile.setId(1);
        mobile.setName("mobile");

        EContactType email = new EContactType();
        email.setDescription("email address");
        email.setId(2);
        email.setName("email");

        when(contactTypeDAO.findAll()).thenReturn(Arrays.asList(mobile, email));
        List<EContactType> contactTypes = sContactType.getAll();
        assertEquals(contactTypes.size(), 2);
        assertEquals(contactTypes.get(0).getName(), "mobile");
        assertEquals(contactTypes.get(1).getName(), "email");
    }

    @Test
    public void testGetContactTypeById() {
        EContactType contactType = new EContactType();
        contactType.setDescription("whatsapp number");
        contactType.setId(3);
        contactType.setName("whatsapp");
        when(contactTypeDAO.findById(3)).thenReturn(Optional.of(contactType));
        EContactType contactTypeById = sContactType.getById(3).get();
        assertEquals(contactTypeById.getName(), "whatsapp");
    }

    @Test
    public void testGetContactTypeByIdNotFound() {
        when(contactTypeDAO.findById(120)).thenThrow(
            new NotFoundException("contact type with specified id not found", "contactTypeId")
        );
        Exception exception = assertThrows(NotFoundException.class, () -> {
            sContactType.getById(120, true);
        });
        assertTrue(exception.getMessage().contains("contact type with specified id not found"));
    }
}
