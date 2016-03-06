package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Category;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryManager {

    Category getCategory(int categoryId, String authToken);

    boolean updateCategory(Category category, String authToken);

    boolean deleteCategory(int categoryId, String authToken);

    boolean addCategory(Category category, String authToken);

    List<Category> getLastUpdatedCategories(Timestamp timestamp, String authToken);

}
