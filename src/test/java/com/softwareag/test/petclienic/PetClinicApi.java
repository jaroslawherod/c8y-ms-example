package com.softwareag.test.petclienic;

import com.cumulocity.model.pagination.PageRequest;
import com.softwareag.petclinic.owner.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class PetClinicApi {
    @Autowired
    RestTemplate platformApi;


    public Owner create(Owner owner) {
        return platformApi.postForObject(baseUrl().path("owners").build().toUri().toString(),owner, Owner.class);
    }

    public List<Owner> listOwners(PageRequest pageRequest) {
        return platformApi.exchange(baseUrl().path("owners")
                .queryParam("pageSize", pageRequest.getPageSize())
                .queryParam("currentPage", pageRequest.getCurrentPage())
                .build().toUri().toString(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<Owner>>() {
        }).getBody();
    }

    private UriComponentsBuilder baseUrl() {
        return UriComponentsBuilder.fromPath("/service/pet-clinic/");
    }

    public void delete(String id) {
        platformApi.delete(baseUrl().path("owners").path(id).build().toUri().toString());
    }
}
