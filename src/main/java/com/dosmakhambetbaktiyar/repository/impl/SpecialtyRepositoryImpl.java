package com.dosmakhambetbaktiyar.repository.impl;

import com.dosmakhambetbaktiyar.database.JdbcUtils;
import com.dosmakhambetbaktiyar.model.Specialty;
import com.dosmakhambetbaktiyar.repository.SpecialtyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public Specialty create(Specialty specialty){
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("INSERT INTO specialty (name,status) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1,specialty.getName());
            statement.setString(2,specialty.getStatus().toString());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                return get(resultSet.getInt(1));
            }
        }catch (SQLException e){
            System.err.println("Specialty create() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public Specialty get(Integer aLong) {
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT * FROM specialty WHERE id = ? AND status = 'ACTIVE'")){

            statement.setInt(1, aLong);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new Specialty(resultSet.getInt("id"),resultSet.getString("name"));
            }
        }catch (SQLException e){
            System.err.println("Specialty get() error. " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Specialty> getAll(){
        List<Specialty> specialties = new ArrayList<>();

        try(Statement statement = JdbcUtils.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM specialty WHERE status = 'ACTIVE'");

            while (resultSet.next()){
                specialties.add(new Specialty(resultSet.getInt("id"),resultSet.getString("name")));
            }
        }catch (SQLException e){
            System.err.println("Specialty getAll() error. " + e.getMessage());
        }


        return specialties;
    }

    @Override
    public boolean delete(Integer aLong) {
        if(checkDevelopers(aLong)) {
            try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE specialty SET status = 'DELETE' WHERE id = ?")) {

                statement.setInt(1, aLong);
                statement.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("Specialty delete() error. " + e.getMessage());
            }
        }
       return false;
    }

    @Override
    public Specialty update(Specialty object){

        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("UPDATE specialty SET name = ? WHERE id = ?")){

            statement.setString(1,object.getName());
            statement.setInt(2,object.getId());
            statement.executeUpdate();

            return get(object.getId());
        }catch (SQLException e){
            System.err.println("Specialty update() error. " + e.getMessage());
        }

        return null;
    }

    private boolean checkDevelopers(Integer id){
        try(PreparedStatement statement = JdbcUtils.getConnection().prepareStatement("SELECT COUNT(d.id) FROM developer d WHERE  d.status = 'ACTIVE' AND d.specialty_id = ?")){

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
