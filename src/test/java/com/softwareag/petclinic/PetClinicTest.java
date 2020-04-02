package com.softwareag;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = PetClinic.class, webEnvironment = RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class PetClinicTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void shouldStartAndExposeHealthEndpoint(){
        //Given
        String url = "http://localhost:"+port+"/health";

        //When
        final ResponseEntity<String> healthResult = rest.getForEntity(url, String.class);

        //Then
        assertTrue(healthResult.getStatusCode().is2xxSuccessful());
    }
}
