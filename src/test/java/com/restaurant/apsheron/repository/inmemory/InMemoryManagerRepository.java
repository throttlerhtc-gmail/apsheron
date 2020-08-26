package com.restaurant.apsheron.repository.inmemory;

import com.restaurant.apsheron.ManagerTestData;
import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.repository.ManagerRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.apsheron.ManagerTestData.ADMIN;
import static com.restaurant.apsheron.ManagerTestData.USER;

@Repository
public class InMemoryManagerRepository extends InMemoryBaseRepository<Manager> implements ManagerRepository {

    public void init() {
        map.clear();
        map.put(ManagerTestData.USER_ID, USER);
        map.put(ManagerTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public List<Manager> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(Manager::getName).thenComparing(Manager::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public Manager getByEmail(String email) {
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}