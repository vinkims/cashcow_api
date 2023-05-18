package com.example.cashcow_api.breed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

    // @Test
    public void testFindById() {
        // EBreed breed = new EBreed();
        // breed.setId(20);
        // breed.setDescription("Drought resistant");
        // breed.setName("Gurnsey");
        EBreed breed = getBreed();
        breedDAO.save(breed);

        EBreed result = breedDAO.findById(breed.getId()).get();
        assertEquals(breed.getId(), result.getId());
    }

    @Test
    public void testFindAll() {
        EBreed breed = getBreed();
        breedDAO.save(breed);
        List<EBreed> result = new ArrayList<>();
        breedDAO.findAll().forEach(b -> result.add(b));
        assertEquals(result.size(), 3);
    }

    @Test
    public void testSave() {
        EBreed breed = getBreed();
        breedDAO.save(breed);
        EBreed result = breedDAO.findById(breed.getId()).get();
        assertEquals(breed.getId(), result.getId());
    }

    private EBreed getBreed() {
        EBreed breed = new EBreed();
        breed.setId(10);
        breed.setDescription("Produces lots of milk");
        breed.setName("Gurnsey");
        return breed;
    }
}
