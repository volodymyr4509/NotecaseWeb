package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Product;

import java.sql.SQLException;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductDAO {
    int addProduct(Product product) throws SQLException;
    Product getProductByProductId(int productId) throws SQLException;
    void updateProduct(Product product) throws SQLException;
    void deleteProduct(int productId) throws SQLException;
}
