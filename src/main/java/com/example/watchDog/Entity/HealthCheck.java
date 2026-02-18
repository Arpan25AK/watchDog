package com.example.watchDog.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthCheck {

   private Integer id;
   private Integer websiteId;
   private Integer websiteStatus;
   private Integer responseTimeMs;
   private LocalDateTime createdAt;
}
