package com.volodymyr.notecase.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by volodymyr on 10.01.16.
 */
@Service
public class ConnectionFactory {
    private static Logger log = Logger.getLogger(ConnectionFactory.class.getName());

//    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/notecase";
    public static final String ENCODING = "?useUnicode=true&characterEncoding=UTF-8";
    @Value("${mysql_user}")
    public String user;
    @Value("${mysql_password}")
    public String password;
    public static final String DRIVER = "com.mysql.jdbc.Driver";

    public ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL + ENCODING, user, password);
            log.debug("Create database connection for Url: " + URL + ", User: " + user);
        } catch (SQLException e) {
            log.error("Cannot connect to database with URL: " + URL + ", USER: " + user + ", PASSWORD: " + password.replaceAll(".", "?"), e);
        }
        return connection;
    }

//    public static Connection getConnection() {
//        log.debug("Get database connection...");
//        return instance.createConnection();
//    }

}
