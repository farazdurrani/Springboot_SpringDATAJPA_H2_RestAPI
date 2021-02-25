package com.hackerrank.eshopping.product.dashboard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;

    @Override
    public boolean addProduct(Product product) {
	if (!productRepo.existsById(product.getId())) {
	    productRepo.save(product);
	    return true;
	}
	return false;
    }

    @Override
    public boolean updateProduct(Product product) {
	if (productRepo.existsById(product.getId())) {
	    productRepo.save(product);
	    return true;
	}
	return false;
    }

    @Override
    public Product getProduct(Long product_id) {
	try {
	    return productRepo.getOne(product_id);
	} catch (EntityNotFoundException e) {
	    return null;
	}

    }

    @Override
    public List<Product> getProductByCategory(String category) {
	return productRepo.findByCategoryContaining(category, Sort.by("availability")
	    .descending().and(Sort.by("discountedPrice")).and(Sort.by("id")));
    }

    @Override
    public List<Product> getProductByCategoryAndAvailability(String category,
        Boolean availability) {
	List<Product> products = productRepo.findByCategoryAndAvailability(category,
	    availability);

	Map<Integer, List<Product>> map = new TreeMap<>(Collections.reverseOrder());
	products.forEach(product -> {
	    Integer discountPerc = (int) (((product.getRetailPrice()
	        - product.getDiscountedPrice()) / product.getRetailPrice()) * 100);
	    if (map.containsKey(discountPerc)) {
		List<Product> pds = map.get(discountPerc);
		pds.add(product);
		map.put(discountPerc, pds);
	    } else {
		List<Product> pds = new ArrayList<>();
		pds.add(product);
		map.put(discountPerc, pds);
	    }
	});
	return map.values().stream().flatMap(productList -> productList.stream())
	    .sorted(Comparator.comparing(Product::getDiscountedPrice))
	    .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> getAllProducts() {
	return productRepo.findAll(Sort.by("id"));
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepo) {
	this.productRepo = productRepo;
    }
}
