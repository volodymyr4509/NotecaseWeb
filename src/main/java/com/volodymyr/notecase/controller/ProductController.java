package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Product;
import org.springframework.web.bind.annotation.*;

/**
 * Created by volodymyr on 03.01.16.
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id) {
        Product product = new Product();
        product.setId(id);
        product.setName("happymeal");
        product.setPrice(12.23);
        return product;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addProduct(@RequestBody Product product) {
        System.out.println(product);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody Product product){
        System.out.println(product);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id){
        System.out.println("Delete: " + id);
    }

}
