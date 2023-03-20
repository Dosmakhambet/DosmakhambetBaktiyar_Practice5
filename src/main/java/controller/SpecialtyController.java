package controller;

import model.Specialty;
import repository.impl.SpecialtyRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepositoryImpl repository;

    public SpecialtyController(SpecialtyRepositoryImpl repository) {
        this.repository = repository;
    }

    public void create(Specialty specialty) throws SQLException {
        repository.create(specialty);
    }

    public Specialty get(Integer id) throws SQLException {
        return repository.get(id);
    }

    public List<Specialty> getAll() throws SQLException {
        return repository.getAll();
    }

    public void delete(Integer id) throws SQLException {
        repository.delete(id);
    }

    public int update(Specialty specialty) throws SQLException {
        return repository.update(specialty);
    }
}
