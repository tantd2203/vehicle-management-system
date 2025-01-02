package com.assignment.vehiclemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class VehicleManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleManagementSystemApplication.class, args);
    }

}
