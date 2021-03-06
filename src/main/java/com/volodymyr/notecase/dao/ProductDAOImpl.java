package com.volodymyr.notecase.dao;

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
public class ProductDAOImpl implements ProductDAO {
    private static Logger log = Logger.getLogger(ProductDAOImpl.class.getName());

    @Autowired
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStmt;

    public Product getProductById(String uuid, int userId) throws SQLException {
        String query = "SELECT * FROM Product WHERE Uuid = '" + uuid + "' AND UserId = " + userId + " AND Enabled = true;";
        ResultSet rs = null;
        Product product = null;
        try {
            connection = connectionFactory.createConnection();
            stmt = connection.createStatement();
            log.info("Database query: " + stmt);

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setUuid(rs.getString("uuid"));
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
    public int addProduct(Product product, int userId) throws SQLException {
        String query = "INSERT INTO Product (Uuid, UserId, CategoryId, Name, Price, Created, Enabled) VALUES (?,?,?,?,?,?,?);";
        int productId;

        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);
            log.info("Database query: " + preparedStmt);

            preparedStmt.setString(1, product.getUuid());
            preparedStmt.setInt(2, userId);
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

    @Override
    public void updateProduct(Product product, int userId) throws SQLException {
        String query = "UPDATE Product SET CategoryId = ?, Name = ?, Price = ?, Enabled = ?, LastUpdateTimestamp = NOW() WHERE Uuid = ? AND  UserId = ? AND Enabled = TRUE;";
        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1, product.getCategoryId());
            preparedStmt.setString(2, product.getName());
            preparedStmt.setDouble(3, product.getPrice());
            preparedStmt.setBoolean(4, product.isEnabled());
            preparedStmt.setString(5, product.getUuid());
            preparedStmt.setInt(6, userId);
            log.info("Database query: " + preparedStmt);

            preparedStmt.executeUpdate();
        } finally {
            DBUtil.close(connection);
            DBUtil.close(preparedStmt);
        }
    }

    @Override
    public void deleteProduct(String uuid, int userId) throws SQLException {
        Product product = getProductById(uuid, userId);
        if (product != null) {
            product.setEnabled(false);
            updateProduct(product, userId);
        }
    }

    @Override
    public List<Product> getLastUpdatedProducts(Timestamp timestamp, int userId, List<User> constituents) throws SQLException {
        String ids = String.valueOf(userId);
        if (constituents!=null){
            for (User f: constituents){
                ids += "," + f.getId();
            }
        }

        String query = "SELECT * FROM Product WHERE UserId IN( " + ids + ") AND LastUpdateTimestamp >= ? AND Enabled = TRUE;";
        List<Product> productList = null;
        ResultSet rs = null;
        try {
            connection = connectionFactory.createConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setTimestamp(1, timestamp);
            log.info("Database query: " + preparedStmt);

            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                if (productList == null) {
                    productList = new ArrayList<>();
                }
                Product product = new Product();
                product.setUuid(rs.getString("Uuid"));
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
