package com.restaurant.apsheron.web.food;

import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.service.FoodService;
import com.restaurant.apsheron.util.FoodsUtil;
import com.restaurant.apsheron.util.ValidationUtil;
import com.restaurant.apsheron.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import com.restaurant.apsheron.to.FoodTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractFoodController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FoodService service;

    public Food get(int id) {
        int userId = SecurityUtil.authManagerId();
        log.info("get food {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authManagerId();
        log.info("delete food {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<FoodTo> getAll() {
        int userId = SecurityUtil.authManagerId();
        log.info("getAll for user {}", userId);
        return FoodsUtil.getTos(service.getAll(userId), SecurityUtil.authManagerCaloriesPerDay());
    }

    public Food create(Food food) {
        int userId = SecurityUtil.authManagerId();
        ValidationUtil.checkNew(food);
        log.info("create {} for user {}", food, userId);
        return service.create(food, userId);
    }

    public void update(Food food, int id) {
        int userId = SecurityUtil.authManagerId();
        ValidationUtil.assureIdConsistent(food, id);
        log.info("update {} for user {}", food, userId);
        service.update(food, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<FoodTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                            @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authManagerId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Food> foodsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return FoodsUtil.getFilteredTos(foodsDateFiltered, SecurityUtil.authManagerCaloriesPerDay(), startTime, endTime);
    }
}