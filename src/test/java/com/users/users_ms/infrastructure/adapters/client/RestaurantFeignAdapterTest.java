// src/test/java/com/users/users_ms/infrastructure/adapters/client/RestaurantFeignAdapterTest.java
package com.users.users_ms.infrastructure.adapters.client;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class RestaurantFeignAdapterTest {

    private RestaurantFeignClient client;
    private RestaurantFeignAdapter adapter;

    @BeforeEach
    void setUp() {
        client = mock(RestaurantFeignClient.class);
        adapter = new RestaurantFeignAdapter(client);
    }

    @Test
    void assignEmployeeToRestaurant_delegatesToClient() {
        Long restaurantId = 10L;
        Long employeeId   = 42L;

        adapter.assignEmployeeToRestaurant(restaurantId, employeeId);

        verify(client).assignEmployee(restaurantId, employeeId);
        verifyNoMoreInteractions(client);
    }
}
