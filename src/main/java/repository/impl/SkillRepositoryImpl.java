package repository.impl;

import model.Skill;
import repository.SkillRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SkillRepositoryImpl implements SkillRepository {

    private Connection connection;

    public SkillRepositoryImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public void create(Skill skill) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("INSERT INTO skill (name,status) VALUES (?,?)");
        statement.setString(1,skill.getName());
        statement.setString(2, skill.getStatus().toString());
        statement.execute();
    }

    @Override
    public Skill get(Integer aLong) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM skill WHERE id = ? WHERE status = 'ACTIVE'");
        statement.setInt(1, aLong);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            return new Skill(resultSet.getInt("id"),resultSet.getString("name"));
        }
        return null;
    }

    @Override
    public List<Skill> getAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM skill WHERE status = 'ACTIVE'");
        ResultSet resultSet = statement.executeQuery();
        List<Skill> skills = new ArrayList<>();

        while (resultSet.next()){
            skills.add(new Skill(resultSet.getInt("id"),resultSet.getString("name")));
        }

        return skills;
    }

    @Override
    public boolean delete(Integer aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE skill SET status = 'DELETE' WHERE id = ?");
        statement.setInt(1, aLong);
        return statement.execute();
    }

    @Override
    public int update(Skill object) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("UPDATE skill SET name = ? WHERE id = ?");
        statement.setString(1,object.getName());
        statement.setInt(2,object.getId());

        return statement.executeUpdate();
    }
}
