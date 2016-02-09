package com.volodymyr.notecase.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by volodymyr on 10.01.16.
 */
public class DBUtil {
    private static Logger log = Logger.getLogger(DBUtil.class.getName());

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                log.debug("Connection closed successfully");
            } catch (SQLException e) {
                log.error("Cannot close Connection.", e);
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
                log.debug("Statement closed successfully");
            } catch (SQLException e) {
                log.error("Cannot close Statement", e);
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                log.debug("ResultSet closed successfully");
            } catch (SQLException e) {
                log.error("Cannot close ResultSet", e);
            }
        }
    }
}
