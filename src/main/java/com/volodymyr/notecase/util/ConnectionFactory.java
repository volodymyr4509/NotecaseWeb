package com.volodymyr.notecase.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ConnectionFactory {
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/notecase";
    public static final String USER = "root";
    public static final String PASSWORD = "12345";
    public static final String DRIVER = "com.mysql.jdbc.Driver";

    private ConnectionFactory(){
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return instance.createConnection();
    }

}
