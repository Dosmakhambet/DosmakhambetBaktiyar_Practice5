package com.dosmakhambetbaktiyar.controller;

import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.service.SkillService;

import java.util.List;

public class SkillController {
    private SkillService service;
    public SkillController(SkillService service){ this.service = service;}

    public Skill create(Skill skill)  { return service.create(skill); }

    public Skill get(Integer id) {
        return service.get(id);
    }

    public List<Skill> getAll() {
        return service.getAll();
    }

    public boolean delete(Integer id) {
       return service.delete(id);
    }

    public Skill update(Skill skill) {
        return service.update(skill);
    }
}
