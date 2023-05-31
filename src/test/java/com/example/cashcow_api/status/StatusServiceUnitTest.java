package com.example.cashcow_api.status;

import com.example.cashcow_api.exceptions.NotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.StatusDAO;
import com.example.cashcow_api.services.status.SStatus;

@ExtendWith(MockitoExtension.class)
public class StatusServiceUnitTest {
    
    @Mock
    private StatusDAO statusDAO;

    @InjectMocks
    private SStatus sStatus;

    @Test
    public void shouldGetStatusById() {
        EStatus status = new EStatus();
        status.setDescription("resource enabled for all corresponding functionality");
        status.setId(1);
        status.setName("active");

        when(statusDAO.findById(1)).thenReturn(Optional.of(status));
        EStatus statusById = sStatus.getById(1).get();
        assertEquals(statusById.getName(), "active");
    }

    @Test
    public void shouldReturnStatusNotFound() {
        when(statusDAO.findById(50)).thenThrow(
            new NotFoundException("status with specified id not found", "statusId")
        );
        Exception exception = assertThrows(NotFoundException.class, () -> {
            sStatus.getById(50, true);
        });
        assertTrue(exception.getMessage().contains("status with specified id not found"));
    }
}
