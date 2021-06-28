package com.exam.kudu_jdbc_api.controller;


import com.exam.kudu_jdbc_api.model.Customer;
import com.exam.kudu_jdbc_api.service.CustomerService;
import com.exam.kudu_jdbc_api.utils.Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }


    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        try{
            customer.setCreatedAt(Utils.getCurrentTimeInMillis());
            customer.setUpdatedAt(Utils.getCurrentTimeInMillis());
            System.out.println(new Gson().toJson(customer));
            customerService.insertCustomer(customer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Customer rsCustomer = customerService.getCustomerById(customer.getId());
        return rsCustomer;
    }

    @PostMapping("/customers/list")
    public ResponseEntity<?> createCustomers(@RequestBody List<Customer> customers) {
        boolean checker = false;
        try{
            for (Customer customer : customers) {
                customer.setCreatedAt(Utils.getCurrentTimeInMillis());
                customer.setUpdatedAt(Utils.getCurrentTimeInMillis());
            }

            System.out.println(new Gson().toJson(customers));
            checker = customerService.insertCustomers(customers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if(checker) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        try{
            Customer oldCustomer = customerService.getCustomerById(customer.getId());

            if (oldCustomer == null) {
                return oldCustomer;
            }

            customer.setUpdatedAt(Utils.getCurrentTimeInMillis());
            System.out.println(new Gson().toJson(customer));
            customerService.updateCustomer(customer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Customer rsCustomer = customerService.getCustomerById(customer.getId());
        return rsCustomer;
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Integer customerId) {
        try {
            customerService.deleteCustomer(customerId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Customer newCustomer = customerService.getCustomerById(customerId);
        if(newCustomer == null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
