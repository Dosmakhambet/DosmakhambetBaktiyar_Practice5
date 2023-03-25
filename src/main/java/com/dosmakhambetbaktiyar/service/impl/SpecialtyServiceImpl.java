package com.dosmakhambetbaktiyar.service.impl;

import com.dosmakhambetbaktiyar.model.Specialty;
import com.dosmakhambetbaktiyar.repository.SpecialtyRepository;
import com.dosmakhambetbaktiyar.service.SpecialtyService;

import java.util.List;

public class SpecialtyServiceImpl implements SpecialtyService {

    private SpecialtyRepository repository;

    public SpecialtyServiceImpl(SpecialtyRepository repository){
        this.repository = repository;
    }

    @Override
    public Specialty create(Specialty specialty) {
        return repository.create(specialty);
    }

    @Override
    public Specialty get(Integer integer) {
        return repository.get(integer);
    }

    @Override
    public List<Specialty> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Integer integer) {
        return repository.delete(integer);
    }

    @Override
    public Specialty update(Specialty object) {
        return repository.update(object);
    }
}
