package com.users.users_ms.domain.ports.out;

public interface RestaurantFeignPort {
    void assignEmployeeToRestaurant(Long restaurantId, Long employeeId);

}
