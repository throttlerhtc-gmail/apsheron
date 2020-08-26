package com.restaurant.apsheron.service.datajpa;

import com.restaurant.apsheron.service.AbstractManagerServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import com.restaurant.apsheron.FoodTestData;
import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.util.exception.NotFoundException;

import static com.restaurant.apsheron.Profiles.DATAJPA;
import static com.restaurant.apsheron.ManagerTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaManagerServiceTest extends AbstractManagerServiceTest {
    @Test
    void getWithFoods() throws Exception {
        Manager admin = service.getWithFoods(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, ADMIN);
        FoodTestData.FOOD_MATCHER.assertMatch(admin.getFoods(), FoodTestData.ADMIN_FOOD2, FoodTestData.ADMIN_FOOD1);
    }

    @Test
    void getWithFoodsNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithFoods(1));
    }
}