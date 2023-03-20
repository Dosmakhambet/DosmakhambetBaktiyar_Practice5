package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private static Database database = null;

    private Database() {
        url = "jdbc:mysql://127.0.0.1:3306/practice_5";
        username = "root";
        password = "";
    }

    private void setConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, username, password);

        }catch (SQLException e){
            throw e;
        }
    }

    public static synchronized Database getInstance() throws SQLException {
        if(database == null){
            database = new Database();
            database.setConnection();
        }
        return database;
    }

    public Connection getConnection() {
        return database.connection;
    }
}
