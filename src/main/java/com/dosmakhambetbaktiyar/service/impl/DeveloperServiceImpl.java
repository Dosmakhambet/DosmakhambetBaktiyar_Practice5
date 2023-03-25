package com.dosmakhambetbaktiyar.service.impl;

import com.dosmakhambetbaktiyar.model.Developer;
import com.dosmakhambetbaktiyar.repository.DeveloperRepository;
import com.dosmakhambetbaktiyar.service.DeveloperService;

import java.util.List;

public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperRepository repository;

    public DeveloperServiceImpl(DeveloperRepository repository){
        this.repository = repository;
    }
    @Override
    public Developer create(Developer developer) {
        return repository.create(developer);
    }

    @Override
    public Developer get(Integer integer) {
        return repository.get(integer);
    }

    @Override
    public List<Developer> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Integer integer) {
        return repository.delete(integer);
    }

    @Override
    public Developer update(Developer object) {
        return repository.update(object);
    }
}
