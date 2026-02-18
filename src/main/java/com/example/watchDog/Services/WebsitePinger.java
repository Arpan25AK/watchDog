package com.example.watchDog.Services;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Repository.Repo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class WebsitePinger {

    private final RestTemplate restTemplate;
    private final Repo websiteRepo;

    public WebsitePinger(RestTemplate restTemplate, Repo websiteRepo) {
        this.websiteRepo = websiteRepo;
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<String> pingSingleSite(String url){
        long startTime = System.currentTimeMillis();
        int statusCode;
        String statusText;

        try {
            var response = restTemplate.getForEntity(url, String.class);
            statusCode = response.getStatusCode().value();
            statusText = "UP";
        }catch(org.springframework.web.client.ResourceAccessException e){
            statusCode = 504;
            statusText = "DOWN (TIMEOUT)";
        }
        catch(Exception e){
            statusCode = 500;
            statusText = "DOWN (ERROR)";
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String result = String.format("URL: %s | Status: %s (%d) | Time: %dms",
                url, statusText, statusCode, duration);

        return CompletableFuture.completedFuture(result);
    }
}
