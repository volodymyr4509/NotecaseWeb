package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class CategoryDAOImpl implements CategoryDAO {
    private static Logger log = Logger.getLogger(CategoryDAOImpl.class.getName());

    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    @Override
    public int addCategory(Category category) throws SQLException {
        String query = "INSERT INTO Category (Id, Name, Color, Image, LastUpdateTimestamp, Enabled) VALUES (?,?,?,?,?,?);";
        int categoryId;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);
            log.info("Database query: " + preparedStmt);
            preparedStmt.setInt(1, category.getId());
            preparedStmt.setString(2, category.getName());
            preparedStmt.setInt(3, category.getColor());
            preparedStmt.setInt(4, category.getImage());
            preparedStmt.setTimestamp(5, category.getLastUpdateTimestamp());
            preparedStmt.setBoolean(6, category.isEnabled());
            categoryId = preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(preparedStmt);
            DBUtil.close(connection);
        }
        return categoryId;
    }

    @Override
    public Category getCategoryById(int id) throws SQLException {
        String query = "SELECT * FROM Category WHERE Id = " + id;
        ResultSet rs = null;
        Category category = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            log.info("Database query: " + stmt);

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("Id"));
                category.setCategoryId(rs.getInt("CategoryId"));
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
    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE Category SET Name = ?, Color = ?, Image = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE Id = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, category.getName());
            preparedStmt.setInt(2, category.getColor());
            preparedStmt.setInt(3, category.getImage());
            preparedStmt.setBoolean(4, category.isEnabled());
            preparedStmt.setInt(5, category.getId());
            log.info("Database query: " + preparedStmt);

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public List<Category> getLastUpdatedProducts(Timestamp timestamp) throws SQLException {
        String query = "SELECT * FROM Category WHERE LastUpdateTimestamp >= ? AND Enabled = true;";
        List<Category> categoryList = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
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

