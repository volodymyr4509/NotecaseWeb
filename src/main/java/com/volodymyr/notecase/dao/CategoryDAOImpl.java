package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;

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
        String query = "INSERT INTO Category (CategoryId, Name, Color, Image, LastUpdateTimestamp, Enabled) VALUES (?,?,?,?,?,?);";
        int categoryId;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);
            log.info("Database query: " + preparedStmt);

            preparedStmt.setInt(1, category.getCategoryId());
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
    public Category getCategoryByCategoryId(int categoryId) throws SQLException {
        String query = "SELECT * FROM Category WHERE CategoryId = " + categoryId;
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
        return category;    }

    @Override
    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE Category SET Name = ?, Color = ?, Image = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE CategoryId = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, category.getName());
            preparedStmt.setInt(2, category.getColor());
            preparedStmt.setInt(3, category.getImage());
            preparedStmt.setBoolean(4, category.isEnabled());
            preparedStmt.setInt(5, category.getCategoryId());
            log.info("Database query: " + preparedStmt);

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }


}

