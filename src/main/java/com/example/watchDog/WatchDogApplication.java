package com.example.watchDog;

import Services.WebsiteCheckService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.watchDog", "Services", "Repository", "Config"})
public class WatchDogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchDogApplication.class, args);
	}

    @Bean
    public CommandLineRunner testMyService(WebsiteCheckService service) {
        return args -> {
            System.out.println("====== WATCHDOG STARTING MANUAL TEST ======");
            service.checkAllWebsites();
            System.out.println("====== WATCHDOG TEST COMPLETE ======");
        };
    }
}
