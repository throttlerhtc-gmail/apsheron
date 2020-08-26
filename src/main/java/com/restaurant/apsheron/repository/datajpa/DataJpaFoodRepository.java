package com.restaurant.apsheron.repository.datajpa;

import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.repository.FoodRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaFoodRepository implements FoodRepository {

    private final CrudFoodRepository crudFoodRepository;
    private final CrudManagerRepository crudManagerRepository;

    public DataJpaFoodRepository(CrudFoodRepository crudFoodRepository, CrudManagerRepository crudManagerRepository) {
        this.crudFoodRepository = crudFoodRepository;
        this.crudManagerRepository = crudManagerRepository;
    }

    @Override
    @Transactional
    public Food save(Food food, int managerId) {
        if (!food.isNew() && get(food.getId(), managerId) == null) {
            return null;
        }
        food.setManager(crudManagerRepository.getOne(managerId));
        return crudFoodRepository.save(food);
    }

    @Override
    public boolean delete(int id, int managerId) {
        return crudFoodRepository.delete(id, managerId) != 0;
    }

    @Override
    public Food get(int id, int managerId) {
        return crudFoodRepository.findById(id)
                .filter(food -> food.getManager().getId() == managerId)
                .orElse(null);
    }

    @Override
    public List<Food> getAll(int managerId) {
        return crudFoodRepository.getAll(managerId);
    }

    @Override
    public List<Food> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int managerId) {
        return crudFoodRepository.getBetweenHalfOpen(startDateTime, endDateTime, managerId);
    }

    @Override
    public Food getWithManager(int id, int managerId) {
        return crudFoodRepository.getWithManager(id, managerId);
    }
}
