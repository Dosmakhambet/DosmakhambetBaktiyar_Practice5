package com.dosmakhambetbaktiyar.service.impl;

import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.repository.SkillRepository;
import com.dosmakhambetbaktiyar.service.SkillService;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private SkillRepository repository;

    public SkillServiceImpl(SkillRepository repository){
        this.repository = repository;
    }

    @Override
    public Skill create(Skill skill) {
        return repository.create(skill);
    }

    @Override
    public Skill get(Integer integer) {
        return repository.get(integer);
    }

    @Override
    public List<Skill> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Integer integer) {
        return repository.delete(integer);
    }

    @Override
    public Skill update(Skill object) {
        return repository.update(object);
    }
}
