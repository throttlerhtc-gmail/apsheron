package com.restaurant.apsheron.service;

import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static com.restaurant.apsheron.FoodTestData.*;
import static com.restaurant.apsheron.ManagerTestData.ADMIN_ID;
import static com.restaurant.apsheron.ManagerTestData.USER_ID;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractFoodServiceTest extends AbstractServiceTest {

    @Autowired
    protected FoodService service;

    @Test
    void delete() throws Exception {
        service.delete(FOOD1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(FOOD1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(FOOD1_ID, ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Food created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Food newFood = getNew();
        newFood.setId(newId);
        FOOD_MATCHER.assertMatch(created, newFood);
        FOOD_MATCHER.assertMatch(service.get(newId, USER_ID), newFood);
    }

    @Test
    void get() throws Exception {
        Food actual = service.get(ADMIN_FOOD_ID, ADMIN_ID);
        FOOD_MATCHER.assertMatch(actual, ADMIN_FOOD1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(FOOD1_ID, ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        Food updated = getUpdated();
        service.update(updated, USER_ID);
        FOOD_MATCHER.assertMatch(service.get(FOOD1_ID, USER_ID), getUpdated());
    }

    @Test
    void updateNotOwn() throws Exception {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(FOOD1, ADMIN_ID));
        Assertions.assertEquals("Not found entity with id=" + FOOD1_ID, exception.getMessage());
    }

    @Test
    void getAll() throws Exception {
        FOOD_MATCHER.assertMatch(service.getAll(USER_ID), FOODS);
    }

    @Test
    void getBetweenInclusive() throws Exception {
        FOOD_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                FOOD3, FOOD2, FOOD1);
    }

    @Test
    void getBetweenWithNullDates() throws Exception {
        FOOD_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), FOODS);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Food(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Food(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Food(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Food(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}