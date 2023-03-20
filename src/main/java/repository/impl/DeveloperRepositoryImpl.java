package repository.impl;


import model.Developer;
import model.Skill;
import model.Specialty;
import repository.DeveloperRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DeveloperRepositoryImpl implements DeveloperRepository {

    private Connection connection;

    public DeveloperRepositoryImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Developer developer) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO developer (firstName, lastName, status, specialty_id) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,developer.getFirstName());
        statement.setString(2,developer.getLastName());
        statement.setString(3,developer.getStatus().toString());
        statement.setInt(4,developer.getSpecialty().getId());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        if(resultSet.next() && developer.getSkills() != null){
            int developer_id = resultSet.getInt(1);
            System.out.println(developer_id);
            PreparedStatement skillStmt = connection.prepareStatement("INSERT INTO developer_skills (developer_id,skill_id) VALUES (?,?)");
            developer.getSkills().forEach(skill -> {
                try {
                    skillStmt.setInt(1,developer_id);
                    skillStmt.setInt(2,skill.getId());
                    skillStmt.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            skillStmt.executeBatch();
        }
        connection.commit();
    }

    @Override
    public Developer get(Integer aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT d.id,d.firstName,d.lastName,s.id,s.name FROM developer d INNER JOIN specialty s ON s.id = d.specialty_id WHERE d.id = ? AND d.status = 'ACTIVE'");
        statement.setInt(1, aLong);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            Developer developer = new Developer(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3));
            if (resultSet.getInt(4) > 0){
                developer.setSpecialty(new Specialty(resultSet.getInt(4),resultSet.getString(5)));
            }
            PreparedStatement skillStmt = connection.prepareStatement("SELECT * FROM skill s INNER JOIN developer_skills ds ON ds.skill_id = s.id WHERE ds.developer_id = ?");
            skillStmt.setInt(1, aLong);
            ResultSet skillResultSet = skillStmt.executeQuery();
            List<Skill> skills = new ArrayList<>();
            while (skillResultSet.next()){
                skills.add(new Skill(skillResultSet.getInt(1),skillResultSet.getString(2)));
            }
            developer.setSkills(skills);
            return developer;
        }

        return null;
    }

    @Override
    public List<Developer> getAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT d.id,d.firstName,d.lastName,s.id,s.name FROM developer d INNER JOIN specialty s ON s.id = d.specialty_id WHERE d.status = 'ACTIVE'");
        ResultSet resultSet = statement.executeQuery();
        List<Developer> developers = new ArrayList<>();
        while(resultSet.next()){
            Developer developer = new Developer(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3));
            if (resultSet.getInt(4) > 0){
                developer.setSpecialty(new Specialty(resultSet.getInt(4),resultSet.getString(5)));
            }
            PreparedStatement skillStmt = connection.prepareStatement("SELECT * FROM skill s INNER JOIN developer_skills ds ON ds.skill_id = s.id WHERE ds.developer_id = ?");
            skillStmt.setInt(1, developer.getId());
            ResultSet skillResultSet = skillStmt.executeQuery();
            List<Skill> skills = new ArrayList<>();
            while (skillResultSet.next()){
                skills.add(new Skill(skillResultSet.getInt(1),skillResultSet.getString(2)));
            }
            developer.setSkills(skills);
            developers.add(developer);
        }

        return developers;
    }

    @Override
    public boolean delete(Integer aLong) throws SQLException {
        connection.setAutoCommit(false);

        PreparedStatement statement = connection.prepareStatement("UPDATE developer SET status = 'DELETED' WHERE id = ?");
        statement.setInt(1, aLong);
        statement.executeUpdate();

        connection.commit();

        return true;
    }

    @Override
    public int update(Developer object) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("UPDATE developer SET firstName = ?, lastName = ?, specialty_id = ? WHERE id = ?");
        statement.setString(1,object.getFirstName());
        statement.setString(2,object.getLastName());
        statement.setInt(3,object.getSpecialty().getId());
        statement.setInt(4,object.getId());
        Integer column = statement.executeUpdate();

        if(object.getSkills() != null){
            PreparedStatement developerSkillStmt = connection.prepareStatement("DELETE FROM developer_skills WHERE developer_id = ?");
            developerSkillStmt.setInt(1,object.getId());
            developerSkillStmt.executeUpdate();
            PreparedStatement skillStmt = connection.prepareStatement("INSERT INTO developer_skills (developer_id,skill_id) VALUES (?,?)");
            object.getSkills().forEach(skill -> {
                try {
                    skillStmt.setInt(1,object.getId());
                    skillStmt.setInt(2,skill.getId());
                    skillStmt.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            skillStmt.executeBatch();
        }

        connection.commit();

        return column;
    }
}
