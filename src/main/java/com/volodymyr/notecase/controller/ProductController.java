package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.manager.ProductManager;
import com.volodymyr.notecase.manager.ProductManagerImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by volodymyr on 03.01.16.
 */

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductManager productManager = new ProductManagerImpl();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id) {
        return productManager.getProduct(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product) {
        return productManager.addProduct(product);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean updateProduct(@RequestBody Product product) {
        return productManager.updateProduct(product);
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public boolean deleteProduct(@PathVariable int productId) {
        return productManager.deleteProduct(productId);
    }

    @RequestMapping(value = "/getupdated/{timestamp}", method = RequestMethod.GET)
    public List<Product> getUpdatedProduct(@PathVariable long timestamp) {
        Timestamp time = new Timestamp(timestamp);
        return productManager.getLastUpdatedProducts(time);
    }

}
