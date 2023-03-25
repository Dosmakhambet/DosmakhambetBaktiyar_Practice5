package com.dosmakhambetbaktiyar.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    public static Connection getConnection(){
        try {
            if(connection == null) {
                loadProperties();
                connection = DriverManager.getConnection(url, username, password);
            }

            return connection;
        }catch (SQLException e){
            System.err.println("Set connection with Database: " + e.getMessage());
        }

        System.exit(1);
        return null;
    }

    private static void loadProperties(){
        try(InputStream input = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
