package com.restaurant.apsheron.repository.datajpa;

import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.repository.ManagerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaManagerRepository implements ManagerRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudManagerRepository crudRepository;

    public DataJpaManagerRepository(CrudManagerRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Manager save(Manager manager) {
        return crudRepository.save(manager);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Manager get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public Manager getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<Manager> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public Manager getWithFoods(int id) {
        return crudRepository.getWithFoods(id);
    }
}
