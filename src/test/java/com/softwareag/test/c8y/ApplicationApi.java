package com.softwareag.test.c8y;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class ApplicationApi {

    @Autowired
    private RestTemplate platformApi;
    @Value("${application.name}")
    private String applicationName;


    public String getApplicationId() {
        final List<Map<String,Object>> byName = (List<Map<String, Object>>) platformApi.getForObject("/application/applications?name={name}", Map.class, Map.of("name", applicationName)).get("applications");
        return String.valueOf(byName.stream().findFirst().get().get("id"));
    }
}
