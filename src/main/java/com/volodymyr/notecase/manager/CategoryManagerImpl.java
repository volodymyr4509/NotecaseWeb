package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.dao.CategoryDAO;
import com.volodymyr.notecase.dao.CategoryDAOImpl;
import com.volodymyr.notecase.entity.Category;
import org.apache.log4j.Logger;

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
            category = categoryDAO.getCategoryByCategoryId(categoryId);
            log.info("Retrieved Category by id = " + categoryId + ", Category: " + category);
        }catch (Exception e){
            log.error("Cannot retrieve category with id = " + categoryId, e);
        }
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        try {
            categoryDAO.updateCategory(category);
            log.info("Updated Category: " + category);
        }catch (Exception e){
            log.error("Cannot update Category: " + category);
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        try {
            Category category = categoryDAO.getCategoryByCategoryId(categoryId);
            if (category != null){
                category.setEnabled(false);
                categoryDAO.updateCategory(category);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addCategory(Category category) {
        try {
            categoryDAO.addCategory(category);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
