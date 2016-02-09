package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.ProductDAO;
import com.volodymyr.notecase.dao.ProductDAOImpl;
import com.volodymyr.notecase.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ProductManagerImpl implements ProductManager {
    private static Logger log = Logger.getLogger(ProductManagerImpl.class.getName());

    private ProductDAO productDAO = new ProductDAOImpl();

    public Product getProduct(int id){
        Product product = null;
        try {
            product = productDAO.getProductById(id);
            log.info("Product retrieved from database: " + product);
        }catch (Exception e){
            log.error("Cannot get Product by id = " + id, e);
        }
        return product;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean success = true;
        try {
            productDAO.updateProduct(product);
            log.info("Product updated successfully: " + product);
        }catch (Exception e){
            log.error("Cannot update Product: " + product, e);
            success = false;
        }
        return success;
    }

    @Override
    public boolean deleteProduct(int productId) {
        boolean success = true;
        try {
            productDAO.deleteProduct(productId);
            log.info("product deleted successfully, id = " + productId);
        }catch (Exception e){
            log.error("Cannot delete Product with id = " + productId, e);
            success = false;
        }
        return success;
    }

    @Override
    public boolean addProduct(Product product) {
        boolean success = true;
        Product duplicate = getProduct(product.getId());
        if (duplicate != null){
            log.info("Product wasnt added. Duplicate product with id = " + product.getId() + " found: " + product);
            return false;
        }
        try {
            productDAO.addProduct(product);
            log.info("Product added: " + product);
        }catch (Exception e){
            log.error("Cannot add Product: " + product, e);
            success = false;
        }
        return success;
    }

    @Override
    public List<Product> getLastUpdatedProducts(Timestamp timestamp) {
        List<Product> productList = null;
        try {
            productList = productDAO.getLastUpdatedProducts(timestamp);
            log.info("Products List retrieved from database, count = " + productList.size() + ", lastUpdateTimestamp = " + timestamp);
        }catch (Exception e){
            log.error("Cannot retrieve last updated products since: " + timestamp, e);
        }
        return productList;
    }

}
