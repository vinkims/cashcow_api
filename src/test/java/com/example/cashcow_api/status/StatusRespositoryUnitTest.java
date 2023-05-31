package com.example.cashcow_api.status;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.StatusDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StatusRespositoryUnitTest {
    
    @Autowired
    private StatusDAO statusDAO;

    @BeforeEach
    public void setup() {
        EStatus activeStatus = new EStatus();
        activeStatus.setDescription("resource enabled for all corresponding functionality");
        activeStatus.setId(1);
        activeStatus.setName("active");
        statusDAO.save(activeStatus);

        EStatus inactiveStatus = new EStatus();
        inactiveStatus.setDescription("resources locked for some/all functionality");
        inactiveStatus.setId(2);
        inactiveStatus.setName("inactive");
        statusDAO.save(inactiveStatus);
    }

    @AfterEach
    public void destroy() {
        statusDAO.deleteAll();
    }

    @Test
    public void shouldFindStatusById() {
        EStatus pendingStatus = new EStatus();
        pendingStatus.setDescription("resource awaiting further action");
        pendingStatus.setId(3);
        pendingStatus.setName("pending");
        statusDAO.save(pendingStatus);

        EStatus status = statusDAO.findById(pendingStatus.getId()).get();
        assertThat(status.getId()).isEqualTo(pendingStatus.getId());
    }

    @Test
    public void testFindAllStatuses() {
        List<EStatus> statuses = statusDAO.findAll();
        assertThat(statuses.size()).isEqualTo(2);
        assertThat(statuses.get(0).getId()).isNotNegative();
        assertThat(statuses.get(0).getName()).isEqualTo("active");
    }

    @Test
    public void shouldCreateStatuses() {
        EStatus status = new EStatus();
        status.setDescription("resource action completed successfully");
        status.setId(10);
        status.setName("complete");

        EStatus status1 = new EStatus();
        status1.setDescription("transaction unsuccesssful");
        status1.setId(11);
        status1.setName("failed");

        EStatus status2 = new EStatus();
        status2.setDescription("cow has been sold");
        status2.setId(12);
        status2.setName("sold");

        List<EStatus> statuses = Arrays.asList(status, status1, status2);
        Iterable<EStatus> allStatuses = statusDAO.saveAll(statuses);

        AtomicInteger validFound = new AtomicInteger();
        allStatuses.forEach(st -> {
            if (st.getId() > 0) {
                validFound.getAndIncrement();
            }
        });

        assertThat(validFound.intValue()).isEqualTo(3);
    }
}
