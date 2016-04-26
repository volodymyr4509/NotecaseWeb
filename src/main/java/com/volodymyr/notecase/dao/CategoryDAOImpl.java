package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.entity.User;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class CategoryDAOImpl implements CategoryDAO {
    private static Logger log = Logger.getLogger(CategoryDAOImpl.class.getName());

    @Autowired
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    @Override
    public int addCategory(Category category, int userId) throws SQLException {
        String query = "INSERT INTO Category (Id, UserId, Name, Color, Image, LastUpdateTimestamp, Enabled) VALUES (?,?,?,?,?,?,?);";
        int categoryId;
        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);
            log.info("Database query: " + preparedStmt);
            preparedStmt.setInt(1, category.getId());
            preparedStmt.setInt(2, userId);
            preparedStmt.setString(3, category.getName());
            preparedStmt.setInt(4, category.getColor());
            preparedStmt.setInt(5, category.getImage());
            preparedStmt.setTimestamp(6, category.getLastUpdateTimestamp());
            preparedStmt.setBoolean(7, category.isEnabled());
            categoryId = preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(preparedStmt);
            DBUtil.close(connection);
        }
        return categoryId;
    }

    @Override
    public Category getCategoryById(int id, int userId) throws SQLException {
        String query = "SELECT * FROM Category WHERE Id = " + id + " AND UserId = " + userId + " AND Enabled = TRUE;";
        ResultSet rs = null;
        Category category = null;
        try {
            connection = connectionFactory.createConnection();
            stmt = connection.createStatement();
            log.info("Database query: " + stmt);

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("Id"));
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setUserId(rs.getInt("UserId"));
                category.setName(rs.getString("Name"));
                category.setColor(rs.getInt("Color"));
                category.setImage(rs.getInt("Image"));
                category.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                category.setEnabled(rs.getBoolean("Enabled"));
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return category;
    }

    @Override
    public void updateCategory(Category category, int userId) throws SQLException {
        String query = "UPDATE Category SET Name = ?, Color = ?, Image = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE Id = ? AND UserId = ? AND Enabled = TRUE;";
        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, category.getName());
            preparedStmt.setInt(2, category.getColor());
            preparedStmt.setInt(3, category.getImage());
            preparedStmt.setBoolean(4, category.isEnabled());
            preparedStmt.setInt(5, category.getId());
            preparedStmt.setInt(6, category.getUserId());
            log.info("Database query: " + preparedStmt);

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public List<Category> getLastUpdatedCategories(Timestamp timestamp, int userId, List<User> userConstituents) throws SQLException {
        String ids = String.valueOf(userId);
        if (connection!=null){
            for (User cons: userConstituents){
                ids += "," + cons.getId();
            }
        }

        String query = "SELECT * FROM Category WHERE UserId IN (" + ids + ") AND LastUpdateTimestamp >= ? AND Enabled = TRUE;";
        List<Category> categoryList = null;
        ResultSet rs = null;
        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setTimestamp(1, timestamp);
            log.info("Database query: " + preparedStmt);

            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                if (categoryList == null) {
                    categoryList = new ArrayList<>();
                }
                Category category = new Category();
                category.setId(rs.getInt("Id"));
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setUserId(rs.getInt("UserId"));
                category.setName(rs.getString("Name"));
                category.setColor(rs.getInt("Color"));
                category.setImage(rs.getInt("Image"));
                category.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                category.setEnabled(rs.getBoolean("Enabled"));
                categoryList.add(category);
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return categoryList;
    }

}

