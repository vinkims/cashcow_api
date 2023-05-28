package com.example.cashcow_api.breed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

import com.example.cashcow_api.models.EBreed;
import com.example.cashcow_api.repositories.BreedDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BreedRepositoryUnitTests {
    
    @Autowired
    private BreedDAO breedDAO;

    @BeforeEach
    public void setup() {
        EBreed breed1 = new EBreed();
        breed1.setId(1);
        breed1.setDescription("Produces lots of milk");
        breed1.setName("Fresian");
        breedDAO.save(breed1);

        EBreed breed2 = new EBreed();
        breed2.setId(2);
        breed2.setDescription("Disease-resistant");
        breed2.setName("Jersey");
        breedDAO.save(breed2);
    }
    
    @AfterEach
    public void destroy() {
        breedDAO.deleteAll();
    }

    @Test
    public void testFindById() {
        EBreed breed = new EBreed();
        breed.setId(5);
        breed.setName("Kienyeji");
        breed.setDescription("Disease and drought resistant");
        breedDAO.save(breed);

        EBreed result = breedDAO.findById(breed.getId()).get();
        assertThat(result.getId()).isEqualTo(breed.getId());
    }

    @Test
    public void testFindAll_success() {
        List<EBreed> breedList = breedDAO.findAll();
        assertThat(breedList.size()).isEqualTo(2);
        assertThat(breedList.get(0).getId()).isNotNegative();
        assertThat(breedList.get(0).getName()).isEqualTo("Fresian");
    }

    @Test
    public void testExistsByName() {
        Boolean breedExists = breedDAO.existsByName("Fresian");
        Boolean breed2 = breedDAO.existsByName("Kienyeji");

        assertThat(breedExists).isEqualTo(true);
        assertThat(breed2).isEqualTo(false);
    }

    @Test
    public void testSave() {
        EBreed breed = new EBreed();
        breed.setId(3);
        breed.setName("Ayrshire");
        breed.setDescription("High milk production potential");

        EBreed breed2 = new EBreed();
        breed2.setId(4);
        breed2.setName("Guernsey");
        breed2.setDescription("Adaptable to all climates");

        List<EBreed> breeds = Arrays.asList(breed, breed2);
        Iterable<EBreed> allBreeds = breedDAO.saveAll(breeds);
        
        AtomicInteger validFound = new AtomicInteger();
        allBreeds.forEach(br -> {
            if (br.getId() > 0) {
                validFound.getAndIncrement();
            }
        });

        assertThat(validFound.intValue()).isEqualTo(2);
    }
}
