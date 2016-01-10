package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;

import java.sql.*;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ProductDAOImpl implements ProductDAO {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    public Product getProductByProductId(int productId) throws SQLException {
        String query = "SELECT * FROM Product WHERE ProductId = " + productId;
        ResultSet rs = null;
        Product product = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("Id"));
                product.setProductId(rs.getInt("ProductId"));
                product.setUserId(rs.getInt("UserId"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setCreatedTimestamp(rs.getTimestamp("CreatedTimestamp"));
                product.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                product.setEnabled(rs.getBoolean("Enabled"));

            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return product;
    }

    @Override
    public int addProduct(Product product) throws SQLException {
        String query = "INSERT INTO Product (ProductId, UserId, CategoryId, Name, Price, CreatedTimestamp, Enabled) VALUES (?, ?,?,?,?,?,?);";
        int productId;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, product.getProductId());
            preparedStmt.setInt(2, product.getUserId());
            preparedStmt.setInt(3, product.getCategoryId());
            preparedStmt.setString(4, product.getName());
            preparedStmt.setDouble(5, product.getPrice());
            preparedStmt.setTimestamp(6, product.getCreatedTimestamp());
            preparedStmt.setBoolean(7, product.isEnabled());
            productId = preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(preparedStmt);
            DBUtil.close(connection);
        }
        return productId;
    }

    //TODO add user to be able to find accurate product
    @Override
    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE Product SET CategoryId = ?, Name = ?, Price = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE ProductId = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, product.getCategoryId());
            preparedStmt.setString(2, product.getName());
            preparedStmt.setDouble(3, product.getPrice());
            preparedStmt.setBoolean(4, product.isEnabled());
            preparedStmt.setInt(5, product.getProductId());

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public void deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Product WHERE ProductId = " + productId + ";";
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        }finally {
            DBUtil.close(connection);
            DBUtil.close(stmt);
        }
    }
}
