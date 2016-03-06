package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Product;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductDAO {
    int addProduct(Product product, int userId) throws SQLException;

    Product getProductById(int id, int userId) throws SQLException;

    void updateProduct(Product product, int userId) throws SQLException;

    void deleteProduct(int id, int userId) throws SQLException;

    List<Product> getLastUpdatedProducts(Timestamp timestamp, int userId) throws SQLException;
}
