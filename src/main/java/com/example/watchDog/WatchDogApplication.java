package com.example.watchDog;

import com.example.watchDog.Services.WebsiteCheckService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.watchDog", "com/example/watchDog/Services", "com/example/watchDog/Repository", "com/example/watchDog/Config"})
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
