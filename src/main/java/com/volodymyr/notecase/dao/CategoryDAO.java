package com.volodymyr.notecase.dao;

import com.volodymyr.notecase.entity.Category;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */
public interface CategoryDAO {

    int addCategory(Category category, int userId) throws SQLException;

    Category getCategoryById(int id, int userId) throws SQLException;

    void updateCategory(Category category, int userId) throws SQLException;

    List<Category> getLastUpdatedProducts(Timestamp timestamp, int userId) throws SQLException;
}
