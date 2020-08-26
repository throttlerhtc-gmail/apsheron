package com.restaurant.apsheron.repository.datajpa;

import com.restaurant.apsheron.model.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudManagerRepository extends JpaRepository<Manager, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Manager m WHERE m.id=:id")
    int delete(@Param("id") int id);

    Manager getByEmail(String email);

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"foods"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Manager m WHERE m.id=?1")
    Manager getWithFoods(int id);
}