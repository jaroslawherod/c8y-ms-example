package com.softwareag.test.c8y;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Component
public class Initializer {

    private final Logger log = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    TenantApi tenantApi;
    @Autowired
    ApplicationApi applicationApi;

    @Autowired
    RestTemplate platformApi;

    @PostConstruct
    public void subscribeAndWaitForStart() throws InterruptedException, TimeoutException {
        final CurrentTenant currentTenant = tenantApi.currentTenant();
        tenantApi.subscribeTo(currentTenant.getName(), applicationApi.getApplicationId());
        OffsetDateTime timeout = OffsetDateTime.now().plusMinutes(5);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < 10; ++i) {
                    final Map info = platformApi.getForObject("/service/pet-clinic/health", Map.class);
                    log.info("Pet Clinic is healthy {}", info);
                }
                return;
            } catch (HttpServerErrorException ex) {
                if (ex.getStatusCode().value() != 502) {
                    throw ex;
                }
                log.info("Is not ready", ex);
            }
            if(timeout.isBefore(OffsetDateTime.now())){
                throw new TimeoutException("Microservice is not avilable after 5 minues");
            }
            Thread.sleep(1000);
        }
    }
}
