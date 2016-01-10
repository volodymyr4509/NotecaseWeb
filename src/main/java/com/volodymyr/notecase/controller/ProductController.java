package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Product;
import com.volodymyr.notecase.manager.ProductManager;
import com.volodymyr.notecase.manager.ProductManagerImpl;
import org.springframework.web.bind.annotation.*;

/**
 * Created by volodymyr on 03.01.16.
 */

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductManager productManager = new ProductManagerImpl();

    @RequestMapping(value = "/get/{productId}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int productId) {
        return productManager.getProduct(productId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addProduct(@RequestBody Product product) {
        productManager.addProduct(product);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody Product product){
        productManager.updateProduct(product);
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int productId){
        productManager.deleteProduct(productId);
    }

}
