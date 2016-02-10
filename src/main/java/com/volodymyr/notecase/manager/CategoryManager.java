package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Category;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryManager {

    Category getCategory(int categoryId);

    boolean updateCategory(Category category);

    boolean deleteCategory(int categoryId);

    boolean addCategory(Category category);

    List<Category> getLastUpdatedCategories(Timestamp timestamp);

}
