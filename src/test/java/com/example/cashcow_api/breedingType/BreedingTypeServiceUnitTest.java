package com.example.cashcow_api.breedingType;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cashcow_api.dtos.breeding.BreedingTypeDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.repositories.BreedingTypeDAO;
import com.example.cashcow_api.services.breeding.SBreedingType;

@ExtendWith(MockitoExtension.class)
public class BreedingTypeServiceUnitTest {
    
    @Mock
    private BreedingTypeDAO breedingTypeDAO;

    @InjectMocks
    private SBreedingType sBreedingType;

    @Test
    public void testCreateBreedingType() {
        BreedingTypeDTO breedingTypeDTO = new BreedingTypeDTO();
        breedingTypeDTO.setDescription("Mating animals of different breeds");
        breedingTypeDTO.setName("Cross Breeding");

        EBreedingType breedingType = sBreedingType.create(breedingTypeDTO);
        verify(breedingTypeDAO, times(1)).save(breedingType);

        ArgumentCaptor<EBreedingType> breedingTypeArgCaptor = ArgumentCaptor.forClass(EBreedingType.class);
        verify(breedingTypeDAO).save(breedingTypeArgCaptor.capture());

        EBreedingType breedingTypeCreated = breedingTypeArgCaptor.getValue();
        assertNotNull(breedingTypeCreated.getDescription());
        assertEquals("Cross Breeding", breedingTypeCreated.getName());
    }

    @Test
    public void testGetBreedingTypeById() {
        EBreedingType breedingType = new EBreedingType();
        breedingType.setDescription("Artificial Insemination with a semen specimen");
        breedingType.setId(3);
        breedingType.setName("Artificial Insemination");
        when(breedingTypeDAO.findById(3)).thenReturn(Optional.of(breedingType));

        EBreedingType breedingTypeById = sBreedingType.getById(3).get();
        assertEquals(breedingTypeById.getName(), "Artificial Insemination");
    }

    @Test
    public void testGetBreedingTypeById_Throws_NotFoundException() {
        when(breedingTypeDAO.findById(100)).thenThrow(
            new NotFoundException("breeding type with specified id not found", "breedingTypeId")
        );
        Exception exception = assertThrows(NotFoundException.class, () -> {
            sBreedingType.getById(100, true);
        });
        assertTrue(exception.getMessage().contains("breeding type with specified id not found"));
    }
}
