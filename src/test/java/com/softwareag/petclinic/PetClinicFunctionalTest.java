package com.softwareag.petclinic;

import com.cumulocity.model.pagination.PageRequest;
import com.softwareag.petclinic.owner.Owner;
import com.softwareag.test.FunctionalTestConfiguration;
import com.softwareag.test.petclienic.PetClinicApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FunctionalTestConfiguration.class)
public class PetClinicFunctionalTest {

    @Autowired
    PetClinicApi petClinicApi;

    @Test
    public void shouldStartOnWhenDeployedToPlatform() {
        final HttpStatus healthStatus = petClinicApi.health();

        assertTrue(healthStatus.is2xxSuccessful());
    }

    @Test
    public void shouldCreateNewOwner() {

        //Given
        Owner owner = new Owner();
        owner.setName("Jaro");

        //When
        petClinicApi.create(owner);

        //Then
        final Owner found = petClinicApi.listOwners(new PageRequest(1, 1)).stream().findFirst().get();
        assertEquals(owner.getName(), found.getName());
        assertNotNull(found.getId());


    }
}
