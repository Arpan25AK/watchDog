package com.example.watchDog.Controller;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Services.WebsiteCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceController {

    private final WebsiteCheckService websiteCheckService;

    public ServiceController(WebsiteCheckService websiteCheckService){
        this.websiteCheckService = websiteCheckService;
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

    @GetMapping
    public String singleSite(@RequestParam String url){
        return websiteCheckService.pingSingleSite(url);
    }

    @GetMapping
    public List<String> multipleSite(@RequestParam String urls){
        return websiteCheckService.pingMultipleSites(urls);
    }
}
