package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.CategoryDAO;
import com.volodymyr.notecase.dao.CategoryDAOImpl;
import com.volodymyr.notecase.entity.Category;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public class CategoryManagerImpl implements CategoryManager {
    private static Logger log = Logger.getLogger(CategoryManagerImpl.class.getName());

    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public Category getCategory(int categoryId) {
        Category category = null;
        try {
            category = categoryDAO.getCategoryById(categoryId);
            log.info("Retrieved Category by id = " + categoryId + ", Category: " + category);
        }catch (Exception e){
            log.error("Cannot retrieve category with id = " + categoryId, e);
        }
        return category;
    }

    @Override
    public boolean updateCategory(Category category) {
        boolean success = true;
        try {
            categoryDAO.updateCategory(category);
            log.info("Updated Category: " + category);
        }catch (Exception e){
            log.error("Cannot update Category: " + category);
            success = false;
        }
        return success;
    }

    @Override
    public boolean deleteCategory(int id) {
        boolean success = true;
        try {
            Category category = categoryDAO.getCategoryById(id);
            if (category != null){
                category.setEnabled(false);
                categoryDAO.updateCategory(category);
                log.info("Category deleted successfully.");
            }
        }catch (Exception e){
            log.error("Cannot delete category", e);
            success = false;
        }
        return success;
    }

    @Override
    public boolean addCategory(Category category) {
        boolean success = true;
        Category duplicate = getCategory(category.getId());
        if (duplicate != null){
            log.info("Category not added. Duplicate Categoory with id = " + category.getId() + " found: " + category);
            return false;
        }
        try {
            categoryDAO.addCategory(category);
            log.info("Category added: " + category);
        }catch (Exception e){
            log.error("Cannot add Category: " + category, e);
            success = false;
        }
        return success;
    }

    @Override
    public List<Category> getLastUpdatedCategories(Timestamp timestamp) {
        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.getLastUpdatedProducts(timestamp);
            log.info("Categories List retrieved from database, count = " + categoryList.size() + ", lastUpdateTimestamp = " + timestamp);
        }catch (Exception e){
            log.error("Cannot retrieve last updated categories since: " + timestamp, e);
        }
        return categoryList;
    }

}
