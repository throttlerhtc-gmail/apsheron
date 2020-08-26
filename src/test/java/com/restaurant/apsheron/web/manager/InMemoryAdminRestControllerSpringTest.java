package com.restaurant.apsheron.web.manager;

import com.restaurant.apsheron.repository.inmemory.InMemoryManagerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.restaurant.apsheron.util.exception.NotFoundException;

import static com.restaurant.apsheron.ManagerTestData.NOT_FOUND;
import static com.restaurant.apsheron.ManagerTestData.USER_ID;

@SpringJUnitConfig(locations = {"classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryManagerRepository repository;

    @BeforeEach
    void setup() throws Exception {
        repository.init();
    }

    @Test
    void delete() throws Exception {
        controller.delete(USER_ID);
        Assertions.assertNull(repository.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}