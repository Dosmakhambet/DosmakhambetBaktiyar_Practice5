package controller;

import model.Skill;
import repository.impl.SkillRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class SkillController {
    private final SkillRepositoryImpl repository;

    public SkillController(SkillRepositoryImpl repository){ this.repository = repository;}

    public void create(Skill skill) throws SQLException { repository.create(skill); }

    public Skill get(Integer id) throws SQLException {
        return repository.get(id);
    }

    public List<Skill> getAll() throws SQLException {
        return repository.getAll();
    }

    public void delete(Integer id) throws SQLException {
        repository.delete(id);
    }

    public int update(Skill skill) throws SQLException {
        return repository.update(skill);
    }
}
