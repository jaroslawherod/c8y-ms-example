package com.softwareag.petclinic;

import com.softwareag.test.petclienic.PetClinicApi;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.softwareag.test.FunctionalTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest( classes = FunctionalTestConfiguration.class)
public class PetClinicFunctionalTest {

    @Autowired
    PetClinicApi petClinicApi;

    @Test
    public void shouldStartOnWhenDeployedToPlatform(){
        final HttpStatus healthStatus = petClinicApi.health();

        assertTrue(healthStatus.is2xxSuccessful());
    }
}
