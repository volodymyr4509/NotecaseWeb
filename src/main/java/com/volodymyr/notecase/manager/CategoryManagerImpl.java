package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.CategoryDAO;
import com.volodymyr.notecase.dao.CategoryDAOImpl;
import com.volodymyr.notecase.dao.UserDAO;
import com.volodymyr.notecase.dao.UserDAOImpl;
import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
@Service
public class CategoryManagerImpl implements CategoryManager {
    private static Logger log = Logger.getLogger(CategoryManagerImpl.class.getName());

    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    @Autowired
    private UserDAO userDAO;

    @Override
    public Category getCategory(int categoryId, String authToken) {
        Category category = null;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null) {
                return null;
            }
            category = categoryDAO.getCategoryById(categoryId, user.getId());
            log.info("Retrieved Category by id = " + categoryId + ", Category: " + category);
        } catch (Exception e) {
            log.error("Cannot retrieve category with id = " + categoryId, e);
        }
        return category;
    }

    @Override
    public boolean updateCategory(Category category, String authToken) {
        boolean success = true;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null) {
                return false;
            }
            categoryDAO.updateCategory(category, user.getId());
            log.info("Updated Category: " + category);
        } catch (Exception e) {
            log.error("Cannot update Category: " + category);
            success = false;
        }
        return success;
    }

    @Override
    public boolean deleteCategory(int id, String authToken) {
        boolean success = false;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            Category category = getCategory(id, authToken);
            if (category != null && user != null) {
                category.setEnabled(false);
                categoryDAO.updateCategory(category, user.getId());
                log.info("Category deleted successfully.");
                success = true;
            }
        } catch (Exception e) {
            log.error("Cannot delete category", e);
            success = false;
        }
        return success;
    }

    @Override
    public boolean addCategory(Category category, String authToken) {
        boolean success = true;
        Category duplicate = getCategory(category.getId(), authToken);
        if (duplicate != null) {
            log.info("Category not added. Duplicate Categoory with id = " + category.getId() + " found: " + category);
            return updateCategory(category, authToken);
        }
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null) {
                return false;
            }
            categoryDAO.addCategory(category, user.getId());
            log.info("Category added: " + category);
        } catch (Exception e) {
            log.error("Cannot add Category: " + category, e);
            success = false;
        }
        return success;
    }

    @Override
    public List<Category> getLastUpdatedCategories(Timestamp timestamp, String authToken) {
        List<Category> categoryList = null;
        try {
            User user = userDAO.getUserByAuthToken(authToken);
            if (user == null) {
                return null;
            }
            categoryList = categoryDAO.getLastUpdatedCategories(timestamp, user.getId(), userDAO.getUserConstituents(user.getId()));
            log.info("Categories List retrieved from database, lastUpdateTimestamp = " + timestamp);
        } catch (Exception e) {
            log.error("Cannot retrieve last updated categories since: " + timestamp, e);
        }
        return categoryList;
    }

}
