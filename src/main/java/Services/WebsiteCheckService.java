package Services;

import Entity.Websites;
import Repository.repo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WebsiteCheckService {

        private final RestTemplate restTemplate;
        private final repo websiteRepo;

        public WebsiteCheckService(RestTemplate restTemplate, repo websiteRepo) {
            this.websiteRepo = websiteRepo;
            this.restTemplate = restTemplate;
        }

        public void checkAllWebsites(){
            List<Websites> activeSites = websiteRepo.findActive();

            for(Websites site : activeSites){
                pingAndRecord(site);
            }
        }

        public void pingAndRecord(Websites site){
            long startTime = System.currentTimeMillis();
            int statusCode;

            try {
                var response = restTemplate.getForEntity(site.getUrl(), site.getClass());
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
