package com.restaurant.apsheron.util;


import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.to.FoodTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.function.Predicate;
import java.util.*;
import java.util.stream.Collectors;

import static com.restaurant.apsheron.util.Util.isBetweenHalfOpen;

public class FoodsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Food> FOODS = Arrays.asList(
            new Food(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Food(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private FoodsUtil() {
    }

    public static List<FoodTo> getTos(Collection<Food> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<FoodTo> getFilteredTos(Collection<Food> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }


    public static List<FoodTo> filterByPredicate(Collection<Food> meals, int caloriesPerDay, Predicate<Food> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Food::getDate, Collectors.summingInt(Food::getCalories))
//                      Collectors.toMap(Food::getDate, Food::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<FoodTo> filteredByCycles(List<Food> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<FoodTo> mealsTo = new ArrayList<>();
        meals.forEach(meal -> {
            if (isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                mealsTo.add(createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsTo;
    }

    public static FoodTo createTo(Food meal, boolean excess) {
        return new FoodTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
