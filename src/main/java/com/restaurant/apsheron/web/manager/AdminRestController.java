package com.restaurant.apsheron.web.manager;

import com.restaurant.apsheron.model.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController extends AbstractManagerController {

    static final String REST_URL = "/rest/admin/managers";

    @GetMapping
    public List<Manager> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Manager get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Manager> createWithLocation(@RequestBody Manager manager) {
        Manager created = super.create(manager);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Manager manager, @PathVariable int id) {
        super.update(manager, id);
    }

    @GetMapping("/by")
    public Manager getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }
}