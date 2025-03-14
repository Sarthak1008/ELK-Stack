package com.example.ElasticSearch.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ElasticSearch.Entity.Product;
import com.example.ElasticSearch.Service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/name/{name}")
    public List<Product> searchByName(@PathVariable String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/description/{keyword}")
    public List<Product> searchByDescription(@PathVariable String keyword) {
        return productService.searchByDescription(keyword);
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}