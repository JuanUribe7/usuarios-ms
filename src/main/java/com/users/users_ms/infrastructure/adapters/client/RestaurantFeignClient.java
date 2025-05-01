package com.users.users_ms.infrastructure.adapters.client;

import com.users.users_ms.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "plazoleta-ms", url = "http://localhost:8085", configuration = FeignConfig.class)
public interface RestaurantFeignClient {

    @PostMapping("/restaurants/{restaurantId}/employees/{employeeId}")
    void assignEmployee(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("employeeId") Long employeeId
    );
}