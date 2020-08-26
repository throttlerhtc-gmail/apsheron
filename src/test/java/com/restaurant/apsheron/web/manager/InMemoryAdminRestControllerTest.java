package com.restaurant.apsheron.web.manager;

import com.restaurant.apsheron.repository.inmemory.InMemoryManagerRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.restaurant.apsheron.util.exception.NotFoundException;

import java.util.Arrays;

import static com.restaurant.apsheron.ManagerTestData.NOT_FOUND;
import static com.restaurant.apsheron.ManagerTestData.USER_ID;

class InMemoryAdminRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;
    private static InMemoryManagerRepository repository;

    @BeforeAll
    static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(AdminRestController.class);
        repository = appCtx.getBean(InMemoryManagerRepository.class);
    }

    @AfterAll
    static void afterClass() {
//        May cause during JUnit "Cache is not alive (STATUS_SHUTDOWN)" as JUnit share Spring context for speed
//        http://stackoverflow.com/questions/16281802/ehcache-shutdown-causing-an-exception-while-running-test-suite
//        appCtx.close();
    }

    @BeforeEach
    void setup() throws Exception {
        // re-initialize
        repository.init();
    }

    @Test
    void delete() throws Exception {
        controller.delete(USER_ID);
        Assertions.assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}