package com.hackerrank.eshopping.product.dashboard.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.hackerrank.eshopping.product.dashboard.model.Product;

public interface ProductService {

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    Product getProduct(Long product_id);

    List<Product> getProductByCategory(String category);

    List<Product> getProductByCategoryAndAvailability(String category, Boolean availability);

    List<Product> getAllProducts();

}
