package com.restaurant.apsheron.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.restaurant.apsheron.model.Auth;
import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.restaurant.apsheron.ManagerTestData.*;

public abstract class AbstractManagerServiceTest extends AbstractServiceTest {

    @Autowired
    protected ManagerService service;

    @Test
    void create() throws Exception {
        Manager created = service.create(getNew());
        int newId = created.id();
        Manager newManager = getNew();
        newManager.setId(newId);
        USER_MATCHER.assertMatch(created, newManager);
        USER_MATCHER.assertMatch(service.get(newId), newManager);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Manager(null, "Duplicate", "user@yandex.ru", "newPass", Auth.USER)));
    }

    @Test
    void delete() throws Exception {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() throws Exception {
        Manager user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() throws Exception {
        Manager user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        Manager updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void getAll() throws Exception {
        List<Manager> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Manager(null, "  ", "mail@yandex.ru", "password", Auth.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Manager(null, "Manager", "  ", "password", Auth.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Manager(null, "Manager", "mail@yandex.ru", "  ", Auth.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Manager(null, "Manager", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Manager(null, "Manager", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())), ConstraintViolationException.class);
    }
}