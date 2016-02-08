package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.ProductDAOImpl;
import com.volodymyr.notecase.entity.Product;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ProductManagerImpl implements ProductManager {
    private ProductDAOImpl productDAO = new ProductDAOImpl();

    public Product getProduct(int id){
        Product product = null;
        productDAO = new ProductDAOImpl();
        try {
            product = productDAO.getProductById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean success = true;
        try {
            productDAO.updateProduct(product);
        }catch (Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean addProduct(Product product) {
        boolean success = true;
        Product duplicate = getProduct(product.getId());
        if (duplicate != null){
            return false;
        }
        try {
            productDAO.addProduct(product);
        }catch (Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}
