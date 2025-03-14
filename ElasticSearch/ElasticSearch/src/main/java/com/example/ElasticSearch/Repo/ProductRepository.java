package com.example.ElasticSearch.Repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.ElasticSearch.Entity.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);
    List<Product> findByDescriptionContaining(String description);
}