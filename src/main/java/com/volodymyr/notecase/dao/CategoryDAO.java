package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryDAO {

    int addCategory(Category category) throws SQLException;

    Category getCategoryById(int id) throws SQLException;

    void updateCategory(Category category) throws SQLException;

    List<Category> getLastUpdatedProducts(Timestamp timestamp) throws SQLException;
}
