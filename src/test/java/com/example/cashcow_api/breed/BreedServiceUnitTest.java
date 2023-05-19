package com.example.cashcow_api.breed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cashcow_api.dtos.cow.BreedDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.repositories.BreedDAO;
import com.example.cashcow_api.services.cow.SBreed;

@ExtendWith(MockitoExtension.class)
public class BreedServiceUnitTest {
    
    @Mock
    private BreedDAO breedDAO;

    @InjectMocks
    private SBreed sBreed;

    @Test
    public void testCreateBreed() {
        BreedDTO breedDTO = new BreedDTO();
        breedDTO.setDescription("Adaptable to all climates");
        breedDTO.setName("Guernsey");
        EBreed breed = sBreed.create(breedDTO);
        verify(breedDAO, times(1)).save(breed);

        ArgumentCaptor<EBreed> breedArgumentCaptor = ArgumentCaptor.forClass(EBreed.class);
        verify(breedDAO).save(breedArgumentCaptor.capture());

        EBreed breedCreated = breedArgumentCaptor.getValue();
        assertNotNull(breedCreated.getDescription());
        assertEquals("Guernsey", breedCreated.getName());
    }

    @Test
    public void testGetBreedList() {
        EBreed breed = new EBreed();
        breed.setDescription("Produces lots of milk");
        breed.setId(1);
        breed.setName("Fresian");

        EBreed breed2 = new EBreed();
        breed2.setDescription("Disease-resistant");
        breed2.setId(2);
        breed2.setName("Jersey");

        when(breedDAO.findAll()).thenReturn(Arrays.asList(breed, breed2));
        List<EBreed> breeds = sBreed.getAll();
        assertEquals(breeds.size(), 2);
        assertEquals(breeds.get(0).getName(), "Fresian");
        assertEquals(breeds.get(1).getName(), "Jersey");
    }

    @Test
    public void testGetBreedById() {
        EBreed breed = new EBreed();
        breed.setDescription("Kienyeji");
        breed.setId(3);
        breed.setName("Kienyeji");
        when(breedDAO.findById(3)).thenReturn(Optional.of(breed));
        EBreed breedById = sBreed.getById(3).get();
        assertEquals(breedById.getName(), "Kienyeji");
    }

    @Test
    public void testGetInvalidBreedById() {
        when(breedDAO.findById(100)).thenThrow(
            new NotFoundException("breed with specified id not found", "breedId")
        );
        Exception exception = assertThrows(NotFoundException.class, () -> {
            sBreed.getById(100, true);
        });
        assertTrue(exception.getMessage().contains("breed with specified id not found"));
    }

    // @Test
    @DisplayName("Unit test for update breed method")
    public void testUpdateBreed() {
        // EBreed breed = new EBreed();
        // breed.setDescription("Drought resistant");
        // breed.setId(5);
        // breed.setName("kienyeji");
        // given(breedDAO.save(breed)).willReturn(breed);
        // when(breedDAO.save(any(EBreed.class))).thenReturn(breed);
        // assertNotNull(breed.getId());
        
        // EBreed savedBreed = breedDAO.save(breed);
        // assertNotNull(savedBreed.getId());

        // BreedDTO updateBreedDTO = new BreedDTO();
        // updateBreedDTO.setDescription("Drought and disease resistant");
        // EBreed updatedBreed = sBreed.update(5, updateBreedDTO);

        // assertEquals(updatedBreed.getDescription(), "Drought and disease resistant");
    }
}
