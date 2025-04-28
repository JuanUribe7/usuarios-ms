package com.users.users_ms.domain.ports.out;

public interface IRestaurantValidationPort {
    boolean isOwnerOfRestaurant(Long restaurantId, Long ownerId);
}
