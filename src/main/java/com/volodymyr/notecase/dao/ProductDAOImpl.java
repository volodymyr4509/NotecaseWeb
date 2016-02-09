package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.util.ConnectionFactory;
import com.volodymyr.notecase.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ProductDAOImpl implements ProductDAO {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM Product WHERE Id = " + id + " AND Enabled = true;";
        ResultSet rs = null;
        Product product = null;
        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setId(rs.getInt("Id"));
                product.setUserId(rs.getInt("UserId"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setCreated(rs.getTimestamp("Created"));
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
        String query = "INSERT INTO Product (Id, UserId, CategoryId, Name, Price, Created, Enabled) VALUES (?, ?,?,?,?,?,?);";
        int productId;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, product.getId());
            preparedStmt.setInt(2, product.getUserId());
            preparedStmt.setInt(3, product.getCategoryId());
            preparedStmt.setString(4, product.getName());
            preparedStmt.setDouble(5, product.getPrice());
            preparedStmt.setTimestamp(6, product.getCreated());
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
        String query = "UPDATE Product SET CategoryId = ?, Name = ?, Price = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE Id = ?;";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, product.getCategoryId());
            preparedStmt.setString(2, product.getName());
            preparedStmt.setDouble(3, product.getPrice());
            preparedStmt.setBoolean(4, product.isEnabled());
            preparedStmt.setInt(5, product.getId());

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public void deleteProduct(int productId) throws SQLException {
        Product product = getProductById(productId);
        if (product!=null){
            product.setEnabled(false);
            updateProduct(product);
        }
    }

    @Override
    public List<Product> getLastUpdatedProducts(Timestamp timestamp) throws SQLException {
        String query = "SELECT * FROM Product WHERE LastUpdateTimestamp >= ? AND Enabled = true;";
        List<Product> productList = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setTimestamp(1, timestamp);
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                if (productList == null){
                    productList = new ArrayList<>();
                }
                Product product = new Product();
                product.setId(rs.getInt("Id"));
                product.setUserId(rs.getInt("UserId"));
                product.setCategoryId(rs.getInt("CategoryId"));
                product.setName(rs.getString("Name"));
                product.setPrice(rs.getDouble("Price"));
                product.setCreated(rs.getTimestamp("Created"));
                product.setLastUpdateTimestamp(rs.getTimestamp("LastUpdateTimestamp"));
                product.setEnabled(rs.getBoolean("Enabled"));
                productList.add(product);
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(connection);
        }
        return productList;
    }

}
