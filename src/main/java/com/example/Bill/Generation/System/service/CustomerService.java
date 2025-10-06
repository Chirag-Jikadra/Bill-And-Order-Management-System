package com.example.Bill.Generation.System.service;

import com.example.Bill.Generation.System.Repository.CustomerRepo;
import com.example.Bill.Generation.System.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;


    public Customer saveCustomer(Customer customer) {
        customerRepo.save(customer);
        return customer;
    }
}
