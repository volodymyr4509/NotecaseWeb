package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.User;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by volodymyr on 19.02.16.
 */
public class UserDAOImpl implements UserDAO {
    private static Logger log = Logger.getLogger(UserDAOImpl.class.getName());

    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM User WHERE Id = " + id + " AND Enabled = true;";
        ResultSet rs = null;
        User user = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            log.info("Database query: " + stmt);

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("UserName"));
                user.setEmail(rs.getString("Email"));
                user.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                user.setEnabled(rs.getBoolean("Enabled"));
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return user;
    }

    @Override
    public int addUser(User user) throws SQLException {
        String query = "INSERT INTO User (Name, Email, AuthToken, Enabled) VALUES (?,?,?,?);";
        int userId;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            log.info("Database query: " + preparedStmt);

            preparedStmt.setString(1, user.getName());
            preparedStmt.setString(2, user.getEmail());
            preparedStmt.setString(3, user.getAuthToken());
            preparedStmt.setBoolean(4, user.isEnabled());

            int affectedRows = preparedStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected");
            }
            try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } finally {
            DBUtil.close(preparedStmt);
            DBUtil.close(connection);
        }
        return userId;
    }

    @Override
    public int updateUser(User user) throws SQLException {
        String query = "UPDATE User SET Name = ?, AuthToken = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE Id = ? AND Email = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, user.getName());
            preparedStmt.setString(2, user.getAuthToken());
            preparedStmt.setBoolean(3, user.isEnabled());
            preparedStmt.setInt(4, user.getId());
            preparedStmt.setString(5, user.getEmail());

            log.info("Database query: " + preparedStmt);

            return preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        User user = getUserById(userId);
        int affectedRows = 0;
        if (user != null) {
            user.setEnabled(false);
            affectedRows = updateUser(user);
        }

        return (affectedRows > 0);
    }

    @Override
    public List<User> getAllTrustedUsers(int userId) throws SQLException {
        String query = "SELECT * FROM User u JOIN UserFriends uf ON u.Id = uf.FriendId WHERE uf.UserId = ?;";
        List<User> users = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, userId);

            log.info("Database query: " + preparedStmt);

            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                if (users == null) {
                    users = new ArrayList<>();
                }

                User user = new User();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("UserName"));
                user.setEmail(rs.getString("Email"));
                user.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                user.setEnabled(rs.getBoolean("Enabled"));

                users.add(user);
            }
        } finally {
            DBUtil.close(connection);
            DBUtil.close(stmt);
            DBUtil.close(rs);
        }
        return users;
    }

    @Override
    public boolean registerIdToken(String idToken) {
        return false;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM User WHERE Email = '" + email + "' AND Enabled = true;";
        ResultSet rs = null;
        User user = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            log.info("Database query: " + stmt);

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setAuthToken(rs.getString("AuthToken"));
                user.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                user.setEnabled(rs.getBoolean("Enabled"));
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return user;
    }
}

