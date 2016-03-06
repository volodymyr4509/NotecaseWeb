package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Product;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductManager {

    Product getProduct(int id, String authToken);

    boolean updateProduct(Product product, String authToken);

    boolean deleteProduct(int id, String authToken);

    boolean addProduct(Product product, String authToken);

    List<Product> getLastUpdatedProducts(Timestamp timestamp, String authToken);

}
