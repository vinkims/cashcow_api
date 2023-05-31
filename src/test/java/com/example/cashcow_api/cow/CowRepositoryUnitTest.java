package com.example.cashcow_api.cow;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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

import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.repositories.CowDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CowRepositoryUnitTest {
    
    @Autowired
    private CowDAO cowDAO;

    @BeforeEach
    public void setup() {
        ECow cow = new ECow();
        cow.setColor("brown");
        cow.setCreatedOn(LocalDateTime.now());
        cow.setGender("Female");
        cow.setId(1);
        cow.setName("Burgei");
        cow.setOtherDetails("Very aggressive");
        cowDAO.save(cow);

        ECow cow1 = new ECow();
        cow1.setColor("white");
        cow1.setCreatedOn(LocalDateTime.now());
        cow1.setGender("Female");
        cow1.setId(2);
        cow1.setName("Sigilai");
        cow1.setOtherDetails("Short tail");
        cowDAO.save(cow1);
    }

    @AfterEach
    public void destroy() {
        cowDAO.deleteAll();
    }

    @Test
    public void shouldReturnCowById() {
        ECow cow2 = new ECow();
        cow2.setColor("black");
        cow2.setCreatedOn(LocalDateTime.now());
        cow2.setGender("Female");
        cow2.setId(3);
        cow2.setName("Kipsafari");
        cow2.setOtherDetails("Bad tempered");
        cowDAO.save(cow2);

        ECow result = cowDAO.findById(cow2.getId()).get();
        assertThat(result.getId()).isEqualTo(cow2.getId());
    }

    @Test
    public void shouldReturnAllCows() {
        List<ECow> cows = cowDAO.findAll();
        assertThat(cows.size()).isEqualTo(2);
        assertThat(cows.get(0).getId()).isNotNegative();
        assertThat(cows.get(0).getName()).isEqualTo("Burgei");
    }

    @Test
    public void shouldReturnCowExistsByName() {
        Boolean cowExists = cowDAO.existsByName("Burgei");
        Boolean anotherCowExists = cowDAO.existsByName("Birirmet");

        assertThat(cowExists).isEqualTo(true);
        assertThat(anotherCowExists).isEqualTo(false);
    }

    @Test
    public void shouldSaveCow() {
        ECow cow3 = new ECow();
        cow3.setColor("black");
        cow3.setCreatedOn(LocalDateTime.now());
        cow3.setGender("Female");
        cow3.setId(4);
        cow3.setName("Vanilla");
        cowDAO.save(cow3);

        ECow cow4 = new ECow();
        cow4.setColor("white");
        cow4.setCreatedOn(LocalDateTime.now());
        cow4.setGender("Female");
        cow4.setId(5);
        cow4.setName("Cherendet");
        cow4.setOtherDetails("Bad tempered");
        cowDAO.save(cow4);

        ECow cow5 = new ECow();
        cow5.setColor("brown");
        cow5.setCreatedOn(LocalDateTime.now());
        cow5.setGender("Female");
        cow5.setId(6);
        cow5.setName("Muranga");
        cow5.setOtherDetails("Very aggressive");
        cowDAO.save(cow5);

        List<ECow> cows = Arrays.asList(cow3, cow4, cow5);
        Iterable<ECow> allCows = cowDAO.saveAll(cows);

        AtomicInteger validFound = new AtomicInteger();
        allCows.forEach(cw -> {
            if (cw.getId() > 0) {
                validFound.getAndIncrement();
            }
        });

        assertThat(validFound.intValue()).isEqualTo(3);
    }
}
