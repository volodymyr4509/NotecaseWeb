package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Product;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductManager {
    Product getProduct(int id);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
    boolean addProduct(Product product);
    List<Product> getLastUpdatedProducts(Timestamp timestamp);

}
