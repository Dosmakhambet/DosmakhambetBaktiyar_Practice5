package com.dosmakhambetbaktiyar.controller;

import com.dosmakhambetbaktiyar.model.Specialty;
import com.dosmakhambetbaktiyar.service.SpecialtyService;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyService service;

    public SpecialtyController(SpecialtyService service) {
        this.service = service;
    }

    public Specialty create(Specialty specialty){
        return service.create(specialty);
    }

    public Specialty get(Integer id){
        return service.get(id);
    }

    public List<Specialty> getAll(){
        return service.getAll();
    }

    public boolean delete(Integer id) {
        return service.delete(id);
    }

    public Specialty update(Specialty specialty) {
        return service.update(specialty);
    }
}
