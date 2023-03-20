package repository.impl;

import model.Specialty;
import repository.SpecialtyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository{

    private Connection connection;

    public SpecialtyRepositoryImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Specialty specialty) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO specialty (name,status) VALUES (?,?)");
        statement.setString(1,specialty.getName());
        statement.setString(2,specialty.getStatus().toString());
        statement.execute();
    }

    @Override
    public Specialty get(Integer aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM specialty WHERE id = ? WHERE status = 'ACTIVE'");
        statement.setInt(1, aLong);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            return new Specialty(resultSet.getInt("id"),resultSet.getString("name"));
        }
        return null;
    }

    @Override
    public List<Specialty> getAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM specialty WHERE status = 'ACTIVE'");
        ResultSet resultSet = statement.executeQuery();
        List<Specialty> specialties = new ArrayList<>();

        while (resultSet.next()){
            specialties.add(new Specialty(resultSet.getInt("id"),resultSet.getString("name")));
        }

        return specialties;
    }

    @Override
    public boolean delete(Integer aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE specialty SET status = 'DELETE' WHERE id = ?");
        statement.setInt(1, aLong);
        return statement.execute();
    }

    @Override
    public int update(Specialty object) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("UPDATE specialty SET name = ? WHERE id = ?");
        statement.setString(1,object.getName());
        statement.setInt(2,object.getId());

        return statement.executeUpdate();
    }
}
