package Controller;

import Entity.Websites;
import Services.WebsiteCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
