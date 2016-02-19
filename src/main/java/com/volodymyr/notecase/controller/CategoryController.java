package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.manager.CategoryManager;
import com.volodymyr.notecase.manager.CategoryManagerImpl;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 10.01.16.
 */

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static Logger log = Logger.getLogger(ProductController.class.getName());

    private CategoryManager categoryManager = new CategoryManagerImpl();

    @RequestMapping(value = "/get/{categoryId}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable int categoryId) {
        log.info("Get Category with id = " + categoryId);
        return categoryManager.getCategory(categoryId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addCategory(@RequestBody Category category) {
        log.info("Add new Category: " + category);
        return categoryManager.addCategory(category);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean updateCategory(@RequestBody Category category) {
        log.info("Update Category with id = " + category.getId() + ", Category: " + category);
        return categoryManager.updateCategory(category);
    }

    @RequestMapping(value = "/delete/{categoryId}")
    public boolean deleteCategory(@PathVariable int categoryId) {
        log.info("Delete Category with id = " + categoryId);
        return categoryManager.deleteCategory(categoryId);
    }

    @RequestMapping(value = "/getupdated/{timestamp}", method = RequestMethod.GET)
    public @ResponseBody List<Category> getUpdatedCategories(@PathVariable long timestamp) {
        Timestamp time = new Timestamp(timestamp);
        log.info("Get updated products from lastUpdateTimestamp = " + time);
        return categoryManager.getLastUpdatedCategories(time);
    }

}
