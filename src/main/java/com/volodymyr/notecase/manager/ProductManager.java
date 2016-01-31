package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Product;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface ProductManager {
    Product getProduct(int id);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    void addProduct(Product product);

}
