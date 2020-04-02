package com.softwareag.petclinic;

import com.softwareag.test.FunctionalTestConfiguration;
import com.softwareag.test.petclienic.PetClinicApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;


@SpringBootTest(classes = FunctionalTestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PetClinicFuncionalTest {

    @Autowired
    PetClinicApi api;


    @Test
    public void shouldExposeHealth(){
        //Given
        String url = "/health";

        //When
        final HttpStatus healthResult = api.health();

        //Then
        assertTrue(healthResult.is2xxSuccessful());
    }
}
