package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody Product product) {
	return new ResponseEntity<>(
	    productService.addProduct(product) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{product_id}")
    public ResponseEntity<Product> update(@PathVariable Long product_id,
        @RequestBody Product product) {
	product.setId(product_id);
	return new ResponseEntity<>(
	    productService.updateProduct(product) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{product_id}")
    public ResponseEntity get(@PathVariable(required = false) Long product_id) {
	if (product_id != null) {
	    Product product = productService.getProduct(product_id);
	    if (null != product) {
		return new ResponseEntity<>(product, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getByCategoryAndAvailability(
        @RequestParam String category, @RequestParam(required = false) Integer availability) {
	if (null != availability) {
	    return new ResponseEntity<>(productService.getProductByCategoryAndAvailability(
	        category, availability == 1 ? true : false), HttpStatus.OK);
	}
	return new ResponseEntity<>(productService.getProductByCategory(category),
	    HttpStatus.OK);
    }

    @Autowired
    public void setProductService(ProductService productService) {
	this.productService = productService;
    }
}
