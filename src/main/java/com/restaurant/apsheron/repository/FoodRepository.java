package com.restaurant.apsheron.repository;


import com.restaurant.apsheron.model.Food;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodRepository {
    // null if updated food do not belong to managerId
    Food save(Food food, int managerId);

    // false if food do not belong to managerId
    boolean delete(int id, int managerId);

    // null if food do not belong to managerId
    Food get(int id, int managerId);

    // ORDERED dateTime desc
    List<Food> getAll(int managerId);

    // ORDERED dateTime desc
    List<Food> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int managerId);

    default Food getWithManager(int id, int managerId) {
        throw new UnsupportedOperationException();
    }
}
