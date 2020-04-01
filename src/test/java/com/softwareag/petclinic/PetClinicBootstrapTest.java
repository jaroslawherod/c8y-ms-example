package com.softwareag.petclinic;

import com.softwareag.petclinc.PetClinic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PetClinic.class, webEnvironment = RANDOM_PORT)
public class PetClinicBootstrapTest {

    @LocalServerPort
    private int localPort;

    TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void shouldStartApplication() {
        //Given
        UriComponents url = baseUrl().path("/health").build();

        //When
        final var entity = rest.getForEntity(url.toUri(), String.class);

        //Then
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    private UriComponentsBuilder baseUrl() {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(localPort);
    }
}
