package com.users.users_ms.infrastructure.adapters.client;

import com.users.users_ms.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "plazoleta-ms", url = "http://localhost:8085", configuration = FeignConfig.class)
public interface RestaurantFeignClient {

    @GetMapping("/restaurants/{restaurantId}/owner/{ownerId}")
    boolean isOwnerOfRestaurant(@PathVariable Long restaurantId, @PathVariable Long ownerId);
}