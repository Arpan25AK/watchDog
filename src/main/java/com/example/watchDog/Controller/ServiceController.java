package com.example.watchDog.Controller;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Services.WebsiteCheckService;
import com.example.watchDog.Services.WebsitePinger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class ServiceController {

    private final WebsiteCheckService websiteCheckService;
    private final WebsitePinger pinger;

    public ServiceController(WebsiteCheckService websiteCheckService, WebsitePinger pinger){
        this.websiteCheckService = websiteCheckService;
        this.pinger = pinger;
    }

    @GetMapping("/reports")
    public List<Websites> getAllStatus(){
        return  websiteCheckService.getAllReports();
    }

    @GetMapping("/instant")
        public List<Websites> getCurrentStatus(){
        websiteCheckService.checkAllWebsites();
        return websiteCheckService.getAllReports();
    }

    @GetMapping("/single")
    public CompletableFuture<String> singleSite(@RequestParam String url){
        return pinger.pingSingleSite(url);
    }

    @GetMapping("/multiple")
    public List<String> multipleSite(@RequestParam String urls){
        return websiteCheckService.pingMultipleSites(urls);
    }

    @PostMapping("/pingAndSave")
    public CompletableFuture<String> pingAndSave(@RequestBody Map<String , String> payload){
        String url = payload.get("url");

        return websiteCheckService.pingAndSaveSite(url);
    }
}
