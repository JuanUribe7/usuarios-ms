package com.users.users_ms.infrastructure.adapters.client;

import com.users.users_ms.domain.ports.out.IRestaurantValidationPort;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFeignAdapter implements IRestaurantValidationPort {

    private final RestaurantFeignClient restaurantFeignClient;

    public RestaurantFeignAdapter(RestaurantFeignClient restaurantFeignClient) {
        this.restaurantFeignClient = restaurantFeignClient;
    }

    @Override
    public boolean isOwnerOfRestaurant(Long restaurantId, Long ownerId) {
        return restaurantFeignClient.isOwnerOfRestaurant(restaurantId, ownerId);
    }
}