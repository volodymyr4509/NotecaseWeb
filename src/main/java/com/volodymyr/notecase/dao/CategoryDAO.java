package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;

import java.sql.SQLException;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryDAO {
    int addCategory(Category category) throws SQLException;

    Category getCategoryByCategoryId(int categoryId) throws SQLException;

    void updateCategory(Category category) throws SQLException;

}
