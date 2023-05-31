package com.example.cashcow_api.contactType;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.cashcow_api.models.EContactType;
import com.example.cashcow_api.repositories.ContactTypeDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ContactTypeRepositoryUnitTest {

    @Autowired
    private ContactTypeDAO contactTypeDAO;

    @BeforeEach
    public void setup() {
        EContactType mobile = new EContactType();
        mobile.setDescription("mobile number");
        mobile.setId(1);
        mobile.setName("mobile");
        contactTypeDAO.save(mobile);

        EContactType email = new EContactType();
        email.setDescription("email address");
        email.setId(2);
        email.setName("email");
        contactTypeDAO.save(email);
    }

    @AfterEach
    public void destroy() {
        contactTypeDAO.deleteAll();
    }

    @Test
    public void testFindById() {
        EContactType contactType = new EContactType();
        contactType.setDescription("whatsapp number");
        contactType.setId(3);
        contactType.setName("whatsapp");
        contactTypeDAO.save(contactType);

        EContactType result = contactTypeDAO.findById(contactType.getId()).get();
        assertThat(result.getId()).isEqualTo(contactType.getId());
    }

    @Test
    public void testFindAllContactTypes() {
        List<EContactType> contactTypeList = contactTypeDAO.findAll();
        assertThat(contactTypeList.size()).isEqualTo(2);
        assertThat(contactTypeList.get(0).getId()).isNotNegative();
        assertThat(contactTypeList.get(0).getName()).isEqualTo("mobile");
    }

    @Test
    public void testCreateContactTypes() {
        EContactType mobile = new EContactType();
        mobile.setDescription("mobile number");
        mobile.setId(1);
        mobile.setName("mobile");

        EContactType email = new EContactType();
        email.setDescription("email address");
        email.setId(2);
        email.setName("email");

        List<EContactType> contactTypes = Arrays.asList(mobile, email);
        Iterable<EContactType> allContactTypes = contactTypeDAO.saveAll(contactTypes);

        AtomicInteger validFound = new AtomicInteger();
        allContactTypes.forEach(ct -> {
            if (ct.getId() > 0) {
                validFound.getAndIncrement();
            }
        });

        assertThat(validFound.intValue()).isEqualTo(2);
    }
}
