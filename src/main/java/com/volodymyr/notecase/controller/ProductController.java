package com.volodymyr.notecase.controller;

import com.volodymyr.notecase.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by volodymyr on 03.01.16.
 */

@RestController
@RequestMapping("/hello")
public class ProductController {

    @RequestMapping("/world")
    public Product greeting() {
        Product product = new Product();
        product.setName("happymeal");
        product.setPrice(12.23);
        return product;
    }
}
