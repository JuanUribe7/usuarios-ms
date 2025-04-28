package com.users.users_ms.domain.ports.in;

import com.users.users_ms.domain.model.User;

import java.util.Optional;

public interface IOwnerServicePort {

    User saveOwner(User user);
    void updateOwnerRestaurantId(Long ownerId, Long restaurantId);
    Optional<User> findById(Long id);



}
