package com.dosmakhambetbaktiyar.repository.impl;


import com.dosmakhambetbaktiyar.database.JdbcUtils;
import com.dosmakhambetbaktiyar.model.Developer;
import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.model.Specialty;
import com.dosmakhambetbaktiyar.repository.DeveloperRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public Developer create(Developer developer){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("INSERT INTO developer (firstName, lastName, status, specialty_id) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
            JdbcUtils.getConnection().setAutoCommit(false);

            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getStatus().toString());
            statement.setInt(4, developer.getSpecialty().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {

                if(developer.getSkills() != null){
                    createDeveloperSkills(resultSet.getInt(1),developer.getSkills());
                }

                return get(resultSet.getInt(1));
            }
        }catch (SQLException e){
            System.err.println("Developer create() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public Developer get(Integer aLong){
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT d.id,d.firstName,d.lastName,s.id,s.name FROM developer d INNER JOIN specialty s ON s.id = d.specialty_id WHERE d.id = ? AND d.status = 'ACTIVE'")) {
            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Developer developer = new Developer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                developer.setSpecialty(new Specialty(resultSet.getInt(4), resultSet.getString(5)));
                developer.setSkills(getDeveloperSkills(aLong));

                return developer;
            }
        }catch (SQLException e){
            System.err.println("Developer get() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Developer> getAll() {
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT d.id,d.firstName,d.lastName,s.id,s.name FROM developer d INNER JOIN specialty s ON s.id = d.specialty_id WHERE d.status = 'ACTIVE'")){

            ResultSet resultSet = statement.executeQuery();
            List<Developer> developers = new ArrayList<>();

            while(resultSet.next()){
                Developer developer = new Developer(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3));
                developer.setSpecialty(new Specialty(resultSet.getInt(4),resultSet.getString(5)));
                developer.setSkills(getDeveloperSkills(developer.getId()));
                developers.add(developer);
            }

            return developers;
        }catch (SQLException e){
            System.err.println("Developer getAll() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean delete(Integer aLong){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE developer SET status = 'DELETED' WHERE id = ?")){

            statement.setInt(1, aLong);
            statement.execute();

            return true;
        }catch (SQLException e){
            System.err.println("Developer delete() error. " + e.getMessage());
        }

        return false;
    }

    @Override
    public Developer update(Developer object){
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE developer SET firstName = ?, lastName = ?, specialty_id = ? WHERE id = ?")) {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setInt(3, object.getSpecialty().getId());
            statement.setInt(4, object.getId());

            int row = statement.executeUpdate();
            if (row > 0 && object.getSkills() != null) {
                deleteDeveloperSkills(object.getId());
                createDeveloperSkills(object.getId(), object.getSkills());
            }

            return get(object.getId());
        }catch (SQLException e){
            System.err.println("Developer update() error. " + e.getMessage());
        }

        return null;
    }

    private int[] createDeveloperSkills(Integer id, List<Skill> skills){

        try(PreparedStatement statement =  JdbcUtils.getConnection().prepareStatement("INSERT INTO developer_skills (developer_id,skill_id) VALUES (?,?)")){

            skills.forEach(skill -> {
                try {
                    statement.setInt(1, id);
                    statement.setInt(2, skill.getId());
                    statement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            return statement.executeBatch();
        }catch (SQLException e){
            System.err.println("Developer createDeveloperSkills() error. " + e.getMessage());
        }

        return null;
    }

    private List<Skill> getDeveloperSkills(Integer id){
        try(PreparedStatement skillStmt = JdbcUtils.getConnection().prepareStatement("SELECT * FROM skill s INNER JOIN developer_skills ds ON ds.skill_id = s.id WHERE ds.developer_id = ?")){

            skillStmt.setInt(1, id);
            ResultSet skillResultSet = skillStmt.executeQuery();
            List<Skill> skills = new ArrayList<>();

            while (skillResultSet.next()){
                skills.add(new Skill(skillResultSet.getInt(1),skillResultSet.getString(2)));
            }

            return skills;
        }catch(SQLException e){
            System.err.println("Developer getDeveloperSkills() error. " + e.getMessage());
        }

        return null;
    }

    private int deleteDeveloperSkills(Integer id){
        try(PreparedStatement developerSkillStmt = JdbcUtils.getConnection().prepareStatement("DELETE FROM developer_skills WHERE developer_id = ?")){
            developerSkillStmt.setInt(1,id);

            return developerSkillStmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Developer deleteDeveloperSkills() error. " + e.getMessage());
        }

        return -1;
    }
}
