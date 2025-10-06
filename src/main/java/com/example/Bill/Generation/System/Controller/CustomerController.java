package com.example.Bill.Generation.System.Controller;

import com.example.Bill.Generation.System.model.Customer;
import com.example.Bill.Generation.System.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/store")
    public ResponseEntity<Customer> saveCustomer(@Validated @RequestBody Customer customer){
        Customer saved = customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
