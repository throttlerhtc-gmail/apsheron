package com.restaurant.apsheron.repository.datajpa;

import com.restaurant.apsheron.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudFoodRepository extends JpaRepository<Food, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Food f WHERE f.id=:id AND f.manager.id=:managerId")
    int delete(@Param("id") int id, @Param("managerId") int managerId);

    @Query("SELECT f FROM Food f WHERE f.manager.id=:managerId ORDER BY f.dateTime DESC")
    List<Food> getAll(@Param("managerId") int managerId);

    @Query("SELECT f from Food f WHERE f.manager.id=:managerId AND f.dateTime >= :startDate AND f.dateTime < :endDate ORDER BY f.dateTime DESC")
    List<Food> getBetweenHalfOpen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("managerId") int managerId);

    @Query("SELECT f FROM Food f JOIN FETCH f.manager WHERE f.id = ?1 and f.manager.id = ?2")
    Food getWithManager(int id, int managerId);
}