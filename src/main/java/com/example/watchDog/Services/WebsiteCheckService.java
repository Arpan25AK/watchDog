package com.example.watchDog.Services;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Repository.Repo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebsiteCheckService {

    private final WebsitePinger pinger;
    private final RestTemplate restTemplate;
    private final Repo websiteRepo;

    public WebsiteCheckService(WebsitePinger pinger, Repo websiteRepo, RestTemplate restTemplate) {
        this.websiteRepo = websiteRepo;
        this.restTemplate = restTemplate;
        this.pinger = pinger;
    }

    public void checkAllWebsites() {
        List<Websites> activeSites = websiteRepo.findActive();

        for (Websites site : activeSites) {
            pinger.pingSingleSite(site.getUrl()).thenAccept(result -> {
                int statusCode = result.contains("UP") ? 200 : (result.contains("DOWN (TIMEOUT)") ? 504 : 500);

                websiteRepo.healthCheckLog(site.getId(), statusCode, 0);
                System.out.println("automated check completed for  :" + site.getUrl());
            });
        }
    }

    public List<Websites> getAllReports() {
        return websiteRepo.detailedHealthLog();
    }


    public List<String> pingMultipleSites(String combinedUrl) {
        return Arrays.stream(combinedUrl.split("\\+"))
                .filter(url -> !url.isBlank())
                .map(String::trim)
                .map(pinger::pingSingleSite) // Start all pings in parallel
                .map(CompletableFuture::join) // Wait for them to finish (max 10s total)
                .collect(Collectors.toList());
    }

    public CompletableFuture<String> pingAndSaveSite(String url) {
        Websites site = websiteRepo.findOrCreate(url);

        return pinger.pingSingleSite(url).thenApply(result -> {
            // 3. Extract status and save (handles auto-delete of old logs)
            int statusCode = result.contains("UP") ? 200 : 500;
            websiteRepo.healthCheckLog(site.getId(), statusCode, 0);
            return result;
        });
    }
}
