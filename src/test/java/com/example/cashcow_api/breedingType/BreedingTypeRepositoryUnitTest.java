package com.example.cashcow_api.breedingType;

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

import com.example.cashcow_api.models.EBreedingType;
import com.example.cashcow_api.repositories.BreedingTypeDAO;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BreedingTypeRepositoryUnitTest {
    
    @Autowired
    private BreedingTypeDAO breedingTypeDAO;

    @BeforeEach
    public void setup() {
        EBreedingType breedingType1 = new EBreedingType();
        breedingType1.setDescription("Natural insemination with a bull");
        breedingType1.setId(1);
        breedingType1.setName("Natural Breeding");
        breedingTypeDAO.save(breedingType1);

        EBreedingType breedingType2 = new EBreedingType();
        breedingType2.setDescription("Artificial Insemination with a semen specimen");
        breedingType2.setId(2);
        breedingType2.setName("Artificial Insemination");
        breedingTypeDAO.save(breedingType2);
    }

    @AfterEach
    public void destroy() {
        breedingTypeDAO.deleteAll();
    }

    @Test
    public void testFindBreedingTypeById() {
        EBreedingType breedingType = new EBreedingType();
        breedingType.setId(4);
        breedingType.setName("Cross Breeding");
        breedingType.setDescription("Mating animals of different breeds");
        breedingTypeDAO.save(breedingType);

        EBreedingType result = breedingTypeDAO.findById(breedingType.getId()).get();
        assertThat(result.getId()).isEqualTo(breedingType.getId());
    }

    @Test
    public void testFindAllBreedingTypes() {
        List<EBreedingType> breedingTypes = breedingTypeDAO.findAll();
        assertThat(breedingTypes.size()).isEqualTo(2);
        assertThat(breedingTypes.get(0).getId()).isNotNegative();
        assertThat(breedingTypes.get(0).getName()).isEqualTo("Natural Breeding");
    }

    @Test
    public void testCreateBreedingType() {
        EBreedingType breedingType = new EBreedingType();
        breedingType.setId(3);
        breedingType.setDescription("Repeated back crossing to one outstanding ancestor");
        breedingType.setName("In breeding");

        EBreedingType breedingType3 = new EBreedingType();
        breedingType3.setId(4);
        breedingType3.setDescription("Mating unrelated animals");
        breedingType3.setName("Out breeding");

        List<EBreedingType> breedingTypeList = Arrays.asList(breedingType, breedingType3);
        Iterable<EBreedingType> allBreedingTypes = breedingTypeDAO.saveAll(breedingTypeList);

        AtomicInteger validFound = new AtomicInteger();
        allBreedingTypes.forEach(brt -> {
            if (brt.getId() > 0) {
                validFound.getAndIncrement();
            }
        });

        assertThat(validFound.intValue()).isEqualTo(2);
    }
}
