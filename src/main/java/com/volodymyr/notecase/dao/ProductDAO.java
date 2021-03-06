package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.entity.User;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductDAO {
    int addProduct(Product product, int userId) throws SQLException;

    Product getProductById(String id, int userId) throws SQLException;

    void updateProduct(Product product, int userId) throws SQLException;

    void deleteProduct(String id, int userId) throws SQLException;

    List<Product> getLastUpdatedProducts(Timestamp timestamp, int userId, List<User> friends) throws SQLException;
}
