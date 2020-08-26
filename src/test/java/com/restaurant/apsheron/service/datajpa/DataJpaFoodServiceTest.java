package com.restaurant.apsheron.service.datajpa;

import com.restaurant.apsheron.service.AbstractFoodServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import com.restaurant.apsheron.ManagerTestData;
import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.util.exception.NotFoundException;

import static com.restaurant.apsheron.FoodTestData.*;
import static com.restaurant.apsheron.Profiles.DATAJPA;
import static com.restaurant.apsheron.ManagerTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
class DataJpaFoodServiceTest extends AbstractFoodServiceTest {
    @Test
    void getWithManager() throws Exception {
        Food adminFood = service.getWithManager(ADMIN_FOOD_ID, ADMIN_ID);
        FOOD_MATCHER.assertMatch(adminFood, ADMIN_FOOD1);
        ManagerTestData.USER_MATCHER.assertMatch(adminFood.getManager(), ManagerTestData.ADMIN);
    }

    @Test
    void getWithManagerNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithManager(1, ADMIN_ID));
    }
}
