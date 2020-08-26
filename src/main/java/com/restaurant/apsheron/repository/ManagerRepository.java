package com.restaurant.apsheron.repository;


import com.restaurant.apsheron.model.Manager;

import java.util.List;

public interface ManagerRepository {
    // null if not found, when updated
    Manager save(Manager manager);

    // false if not found
    boolean delete(int id);

    // null if not found
    Manager get(int id);

    // null if not found
    Manager getByEmail(String email);

    List<Manager> getAll();

    default Manager getWithFoods(int id) {
        throw new UnsupportedOperationException();
    }
}