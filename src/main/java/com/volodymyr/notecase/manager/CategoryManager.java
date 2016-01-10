package com.volodymyr.notecase.manager;

import com.volodymyr.notecase.entity.Category;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryManager {
    Category getCategory(int categoryId);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    void addCategory(Category category);

}
