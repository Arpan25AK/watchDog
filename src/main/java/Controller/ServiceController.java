package Controller;

import Services.WebsiteCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class ServiceController {

    private final WebsiteCheckService websiteCheckService;

    public ServiceController(WebsiteCheckService websiteCheckService){
        this.websiteCheckService = websiteCheckService;
    }

    @GetMapping("reports")
    public getAllStatus(){
        return  websiteCheckService.checkAllWebsites();
    }
}
