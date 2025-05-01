package com.users.users_ms.infrastructure.adapters.client;

import com.users.users_ms.domain.model.Role;
import com.users.users_ms.domain.ports.out.RestaurantFeignPort;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFeignAdapter implements RestaurantFeignPort {

    private final RestaurantFeignClient client;

    public RestaurantFeignAdapter(RestaurantFeignClient client) {
        this.client = client;
    }


    @Override
    public void assignEmployeeToRestaurant(Long restaurantId, Long employeeId) {
        client.assignEmployee(restaurantId, employeeId);
    }


}