package com.example.watchDog.Services;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Repository.Repo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class WebsitePinger {

    private final RestTemplate restTemplate;
    private final Repo websiteRepo;

    public WebsitePinger(RestTemplate restTemplate, Repo websiteRepo) {
        this.websiteRepo = websiteRepo;
        this.restTemplate = restTemplate;
    }
    @Async
    public void pingAndRecord(Websites site){
        long startTime = System.currentTimeMillis();
        int statusCode;

        try {
            var response = restTemplate.getForEntity(site.getUrl(), String.class);
            statusCode = response.getStatusCode().value();
        }
        catch(Exception e){
            statusCode = 500;
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        int responseTime = (int)(endTime - startTime);

        websiteRepo.healthCheckLog(site.getId(),statusCode,responseTime);

        System.out.println(site.getUrl() + " is being checked and" + " its response code is " + statusCode + " and its response time is "  + responseTime);
    }
}
