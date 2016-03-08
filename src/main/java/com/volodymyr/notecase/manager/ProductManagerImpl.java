package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.ProductDAO;
import com.volodymyr.notecase.dao.ProductDAOImpl;
import com.volodymyr.notecase.dao.UserDAO;
import com.volodymyr.notecase.dao.UserDAOImpl;
import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.entity.User;
import org.apache.log4j.Logger;

import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class ProductManagerImpl implements ProductManager {
    private static Logger log = Logger.getLogger(ProductManagerImpl.class.getName());

    private ProductDAO productDAO = new ProductDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();

    public Product getProduct(int id, String authToken){
        Product product = null;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user!=null){
                product = productDAO.getProductById(id, user.getId());
                log.info("Product retrieved from database: " + product);
            }
        }catch (Exception e){
            log.error("Cannot get Product by id = " + id, e);
        }
        return product;
    }

    @Override
    public boolean updateProduct(Product product, String authToken) {
        boolean success = true;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user==null){
                return false;
            }
            productDAO.updateProduct(product, user.getId());
            log.info("Product updated successfully: " + product);
        }catch (Exception e){
            log.error("Cannot update Product: " + product, e);
            success = false;
        }
        return success;
    }

    @Override
    public boolean deleteProduct(int id, String authToken) {
        boolean success = true;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null){
                return false;
            }
            productDAO.deleteProduct(id, user.getId());
            log.info("product deleted successfully, id = " + id);
        }catch (Exception e){
            log.error("Cannot delete Product with id = " + id, e);
            success = false;
        }
        return success;
    }

    /**
     * return true even if duplicate exists
     */
    @Override
    public boolean addProduct(Product product, String authToken) {
        boolean success = true;

        try {
            User user = userDAO.getUserByAuthToken(authToken);
            Product duplicate = getProduct(product.getId(), authToken);
            if (duplicate == null && user != null){
                productDAO.addProduct(product, user.getId());
                log.info("Product added: " + product);
            }
        }catch (Exception e){
            log.error("Cannot add Product: " + product, e);
            success = false;
        }
        return success;
    }

    @Override
    public List<Product> getLastUpdatedProducts(Timestamp timestamp, String authToken) {
        List<Product> productList = null;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null){
                return null;
            }
            productList = productDAO.getLastUpdatedProducts(timestamp, user.getId(), userDAO.getUserConstituents(user.getId()));
            log.info("Products List retrieved from database, lastUpdateTimestamp = " + timestamp);
        }catch (Exception e){
            log.error("Cannot retrieve last updated products since: " + timestamp, e);
        }
        return productList;
    }

}
