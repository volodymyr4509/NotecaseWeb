package com.volodymyr.notecase.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ConnectionFactory {
    private static Logger log = Logger.getLogger(ConnectionFactory.class.getName());

    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/notecase";
    public static final String ENCODING = "?useUnicode=true&characterEncoding=UTF-8";
    public static final String USER = "root";
    public static final String PASSWORD = "*******";
    public static final String DRIVER = "com.mysql.jdbc.Driver";

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL + ENCODING, USER, PASSWORD);
            log.debug("Create database connection for Url: " + URL + ", User: " + USER);
        } catch (SQLException e) {
            log.error("Cannot connect to database with URL: " + URL + ", USER: " + USER + ", PASSWORD: " + PASSWORD.replaceAll(".", "?"));
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        log.debug("Get database connection...");
        return instance.createConnection();
    }

}
