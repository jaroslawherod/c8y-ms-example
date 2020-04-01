package com.softwareag.petclinc;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;
import org.springframework.boot.SpringApplication;

@MicroserviceApplication
public class PetClinic {
    public static void main(String[] args) {
        SpringApplication.run(PetClinic.class);
    }
}
