package com.example.cashcow_api.cow;

import com.example.cashcow_api.exceptions.NotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cashcow_api.dtos.cow.CowDTO;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.repositories.CowDAO;
import com.example.cashcow_api.services.cow.SCow;

@ExtendWith(MockitoExtension.class)
public class CowServiceUnitTest {
    
    @Mock
    private CowDAO cowDAO;

    @InjectMocks
    private SCow sCow;

    @Test
    public void shouldCreateCow() {
        CowDTO cowDTO = new CowDTO();
        cowDTO.setColor("black");
        cowDTO.setGender("Female");
        cowDTO.setName("Lengenet");

        ECow cow = sCow.create(cowDTO);
        verify(cowDAO, times(1)).save(cow);

        ArgumentCaptor<ECow> cowArgCaptor = ArgumentCaptor.forClass(ECow.class);
        verify(cowDAO).save(cowArgCaptor.capture());

        ECow cowCreated = cowArgCaptor.getValue();
        assertNotNull(cowCreated.getName());
        assertEquals("LENGENET", cowCreated.getName());
    }

    @Test
    public void shouldReturnCows() {
        ECow cow = new ECow();
        cow.setColor("white");
        cow.setGender("Male");
        cow.setId(1);
        cow.setName("Benson");

        ECow cow1 = new ECow();
        cow1.setColor("white");
        cow1.setGender("Female");
        cow1.setId(2);
        cow1.setName("Githunguri");

        when(cowDAO.findAll()).thenReturn(Arrays.asList(cow, cow1));
        List<ECow> cows = sCow.getAll();
        assertEquals(cows.size(), 2);
        assertEquals(cows.get(0).getName(), "Benson");
        assertEquals(cows.get(1).getName(), "Githunguri");
    }

    @Test
    public void shouldReturnCowById() {
        ECow cow = new ECow();
        cow.setColor("brown");
        cow.setGender("Female");
        cow.setId(3);
        cow.setName("Vanilla");
        when(cowDAO.findById(3)).thenReturn(Optional.of(cow));
        ECow cowById = sCow.getById(3).get();
        assertEquals(cowById.getName(), "Vanilla");
    }

    @Test
    public void shouldReturnCowNotFound() {
        when(cowDAO.findById(60)).thenThrow(
            new NotFoundException("cow with specified id not found", "cowId")
        );
        Exception exception = assertThrows(NotFoundException.class, () -> {
            sCow.getById(60, true);
        });
        assertTrue(exception.getMessage().contains("cow with specified id not found"));
    }
}
