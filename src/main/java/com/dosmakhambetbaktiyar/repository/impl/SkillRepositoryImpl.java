package com.dosmakhambetbaktiyar.repository.impl;

import com.dosmakhambetbaktiyar.database.JdbcUtils;
import com.dosmakhambetbaktiyar.model.Skill;
import com.dosmakhambetbaktiyar.repository.SkillRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SkillRepositoryImpl implements SkillRepository {

    @Override
    public Skill create(Skill skill){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("INSERT INTO skill (name,status) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,skill.getName());
            statement.setString(2, skill.getStatus().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                return get(resultSet.getInt(1));
            }
        }catch (SQLException e){
            System.err.println("Skill create() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public Skill get(Integer aLong){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT * FROM skill WHERE id = ? AND status = 'ACTIVE'")) {

            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new Skill(resultSet.getInt("id"),resultSet.getString("name"));
            }

        }catch (SQLException e){
            System.err.println("Skill get() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Skill> getAll(){
        List<Skill> skills = new ArrayList<>();

        try(Statement statement = JdbcUtils.getConnection().createStatement()){

            ResultSet resultSet = statement.executeQuery("SELECT * FROM skill WHERE status = 'ACTIVE'");

            while (resultSet.next()){
                skills.add(new Skill(resultSet.getInt("id"),resultSet.getString("name")));
            }

        }catch (SQLException e){
            System.err.println("Skill getAll() error. " + e.getMessage());
        }

        return skills;
    }

    @Override
    public boolean delete(Integer aLong){
        if(checkDevelopers(aLong)) {
            try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE skill SET status = 'DELETE' WHERE id = ?")) {

                statement.setInt(1, aLong);
                statement.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("Skill delete() error. " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Skill update(Skill object){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE skill SET name = ? WHERE id = ?")){

            statement.setString(1,object.getName());
            statement.setInt(2,object.getId());
            statement.executeUpdate();


             return get(object.getId());
        }catch (SQLException e){
            System.err.println("Skill update() error. " + e.getMessage());
        }

        return null;
    }

    private boolean checkDevelopers(Integer id){
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT COUNT(d.id) FROM developer d INNER JOIN developer_skills ds ON ds.developer_id = d.id WHERE  d.status = 'ACTIVE' AND ds.skill_id = ?")){

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                if(resultSet.getInt(1) == 0)
                    return true;
                else
                    return false;
            }
            return false;
        }catch (SQLException e){
            System.err.println("Skill delete() error. " + e.getMessage());
        }

        return false;
    }
}
