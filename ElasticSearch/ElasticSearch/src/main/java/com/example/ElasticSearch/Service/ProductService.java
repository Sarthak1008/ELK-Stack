package com.example.ElasticSearch.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ElasticSearch.Entity.Product;
import com.example.ElasticSearch.Repo.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> searchByDescription(String keyword) {
        return productRepository.findByDescriptionContaining(keyword);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

