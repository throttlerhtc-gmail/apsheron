package com.restaurant.apsheron.service;

import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.repository.ManagerRepository;
import com.restaurant.apsheron.util.ValidationUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.restaurant.apsheron.util.ValidationUtil.checkNotFound;
import static com.restaurant.apsheron.util.ValidationUtil.checkNotFoundWithId;


@Service
public class ManagerService {

    private final ManagerRepository repository;

    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "managers", allEntries = true)
    public Manager create(Manager manager) {
        Assert.notNull(manager, "manager must not be null");
        return repository.save(manager);
    }

    @CacheEvict(value = "managers", allEntries = true)
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public Manager get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public Manager getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return ValidationUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("managers")
    public List<Manager> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "managers", allEntries = true)
    public void update(Manager manager) {
        Assert.notNull(manager, "manager must not be null");
        checkNotFoundWithId(repository.save(manager), manager.id());
    }

    public Manager getWithFoods(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.getWithFoods(id), id);
    }
}