package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hackerrank.eshopping.product.dashboard.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryContaining(String category, Sort sort);

    List<Product> findByCategoryAndAvailability(String category, Boolean availability);
}
