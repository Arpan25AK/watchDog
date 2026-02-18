package com.example.watchDog.Services;

import com.example.watchDog.Entity.Websites;
import com.example.watchDog.Repository.Repo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

        public void checkAllWebsites(){
            List<Websites> activeSites = websiteRepo.findActive();

            for(Websites site : activeSites){
                pinger.pingAndRecord(site);
            }
        }

        public List<Websites> getAllReports(){
            return websiteRepo.detailedHealthLog();
        }

        public String pingSingleSite(String url){
            long startTime = System.currentTimeMillis();
            int statusCode;

            try {
                var response = restTemplate.getForEntity(url, String.class);
                statusCode = response.getStatusCode().value();
                long endTime = System.currentTimeMillis();
                int responseTime = (int)(endTime - startTime);
                return "URL :" + url + "websiteStatus :"  + statusCode + "responseTime :" + responseTime;
            }
            catch(Exception e){
                statusCode = 500;
                e.printStackTrace();
                return "StatusCode :" +  statusCode + "website is not reachable";
            }
        }

        public List<String> pingMultipleSites(String combinedUrl){
            String[] urls =  combinedUrl.split("\\+");
            List<String> result = new ArrayList<>();

            for(String url : urls){
                result.add(pingSingleSite(url.trim()));
            }
            return result;
        }

        public String pingAndSaveSite(String url){
            Websites site = websiteRepo.findOrCreate(url);

            String result = pingSingleSite(url);

            int StatusCode = result.contains("200") ? 200 : 500;

            websiteRepo.healthCheckLog(site.getId(), StatusCode , 0);

            return  "tested and saved" + result;
        }
}
