package com.softwareag.test.c8y;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TenantApi {
    @Autowired
    RestTemplate platformApi;

    public CurrentTenant currentTenant() {
        return platformApi.getForObject("/tenant/currentTenant", CurrentTenant.class);
    }

    public void subscribeTo(String tenant, String application) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","application/vnd.com.nsn.cumulocity.applicationReference+json");
            HttpEntity<Map> entity = new HttpEntity<>(Map.of(
                    "application", Map.of("id", application)
            ),headers);
            platformApi.exchange("/tenant/tenants/{tenant}/applications", HttpMethod.POST, entity, Void.class, Map.of("tenant", tenant));
        }catch (HttpClientErrorException ex){
            if(ex.getStatusCode().value() == 409){
                return;
            }
            throw ex;
        }
    }
}
