package com.restaurant.apsheron.repository.inmemory;

import com.restaurant.apsheron.FoodTestData;
import com.restaurant.apsheron.ManagerTestData;
import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.repository.FoodRepository;
import com.restaurant.apsheron.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryFoodRepository implements FoodRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryFoodRepository.class);

    // Map  managerId -> foodRepository
    private final Map<Integer, InMemoryBaseRepository<Food>> managersFoodsMap = new ConcurrentHashMap<>();

    {
        var managerFoods = new InMemoryBaseRepository<Food>();
        FoodTestData.FOODS.forEach(food -> managerFoods.map.put(food.getId(), food));
        managersFoodsMap.put(ManagerTestData.USER_ID, managerFoods);
    }


    @Override
    public Food save(Food food, int managerId) {
        var foods = managersFoodsMap.computeIfAbsent(managerId, uid -> new InMemoryBaseRepository<>());
        return foods.save(food);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int managerId) {
        var foods = managersFoodsMap.get(managerId);
        return foods != null && foods.delete(id);
    }

    @Override
    public Food get(int id, int managerId) {
        var foods = managersFoodsMap.get(managerId);
        return foods == null ? null : foods.get(id);
    }

    @Override
    public List<Food> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int managerId) {
        return filterByPredicate(managerId, food -> Util.isBetweenHalfOpen(food.getDateTime(), startDateTime, endDateTime));
    }

    @Override
    public List<Food> getAll(int managerId) {
        return filterByPredicate(managerId, food -> true);
    }

    private List<Food> filterByPredicate(int managerId, Predicate<Food> filter) {
        var foods = managersFoodsMap.get(managerId);
        return foods == null ? Collections.emptyList() :
                foods.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Food::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}