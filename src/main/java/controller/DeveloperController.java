package controller;

import model.Developer;
import repository.impl.DeveloperRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class DeveloperController {
    private final DeveloperRepositoryImpl repository;

    public DeveloperController(DeveloperRepositoryImpl repository) {
        this.repository = repository;
    }

    public void create(Developer developer) throws SQLException { repository.create(developer); }

    public Developer get(Integer id) throws SQLException {
        return repository.get(id);
    }

    public List<Developer> getAll() throws SQLException {
        return repository.getAll();
    }

    public void delete(Integer id) throws SQLException {
        repository.delete(id);
    }

    public int update(Developer developer) throws SQLException {
        return repository.update(developer);
    }
}
