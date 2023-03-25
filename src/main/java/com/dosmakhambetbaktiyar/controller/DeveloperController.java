package com.dosmakhambetbaktiyar.controller;

import com.dosmakhambetbaktiyar.model.Developer;
import com.dosmakhambetbaktiyar.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private DeveloperService service;
    public DeveloperController(DeveloperService service) {
        this.service = service;
    }

    public Developer create(Developer developer) { return service.create(developer); }

    public Developer get(Integer id) {
        return service.get(id);
    }

    public List<Developer> getAll() {
        return service.getAll();
    }

    public boolean delete(Integer id) {
        return service.delete(id);
    }

    public Developer update(Developer developer) {
        return service.update(developer);
    }
}
