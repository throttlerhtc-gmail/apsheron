package com.restaurant.apsheron.service;

import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.repository.FoodRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.restaurant.apsheron.util.DateTimeUtil.atStartOfDayOrMin;
import static com.restaurant.apsheron.util.DateTimeUtil.atStartOfNextDayOrMax;
import static com.restaurant.apsheron.util.ValidationUtil.checkNotFoundWithId;

@Service
public class FoodService {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    public Food get(int id, int managerId) {
        return checkNotFoundWithId(repository.get(id, managerId), id);
    }

    public void delete(int id, int managerId) {
        checkNotFoundWithId(repository.delete(id, managerId), id);
    }

    public List<Food> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int managerId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), managerId);
    }

    public List<Food> getAll(int managerId) {
        return repository.getAll(managerId);
    }

    public void update(Food food, int managerId) {
        Assert.notNull(food, "food must not be null");
        checkNotFoundWithId(repository.save(food, managerId), food.id());
    }

    public Food create(Food food, int managerId) {
        Assert.notNull(food, "food must not be null");
        return repository.save(food, managerId);
    }

    public Food getWithManager(int id, int managerId) {
        return checkNotFoundWithId(repository.getWithManager(id, managerId), id);
    }
}