package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Category;
import com.volodymyr.notecase.manager.CategoryManager;
import com.volodymyr.notecase.manager.CategoryManagerImpl;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

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
        log.info("Get Category with id = "  + categoryId);
        return categoryManager.getCategory(categoryId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addCategory(@RequestBody Category category) {
        log.info("Add new Category: " + category);
        categoryManager.addCategory(category);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateCategory(@RequestBody Category category) {
        log.info("Update Category with id = " + category.getId() + ", Category: " + category);
        categoryManager.updateCategory(category);
    }

    @RequestMapping(value = "/delete/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId) {
        log.info("Delete Category with id = " + categoryId);
        categoryManager.deleteCategory(categoryId);
    }

}
