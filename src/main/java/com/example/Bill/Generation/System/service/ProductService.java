package com.example.Bill.Generation.System.service;

import com.example.Bill.Generation.System.Repository.ProductRepo;
import com.example.Bill.Generation.System.model.Customer;
import com.example.Bill.Generation.System.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void saveProduct(Product product) {
        productRepo.save(product);
    }
}
