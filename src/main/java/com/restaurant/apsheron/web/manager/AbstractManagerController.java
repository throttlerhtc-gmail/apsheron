package com.restaurant.apsheron.web.manager;

import com.restaurant.apsheron.model.Manager;
import com.restaurant.apsheron.service.ManagerService;
import com.restaurant.apsheron.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractManagerController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ManagerService service;

    public List<Manager> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Manager get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Manager create(Manager manager) {
        log.info("create {}", manager);
        ValidationUtil.checkNew(manager);
        return service.create(manager);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Manager manager, int id) {
        log.info("update {} with id={}", manager, id);
        ValidationUtil.assureIdConsistent(manager, id);
        service.update(manager);
    }

    public Manager getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }
}