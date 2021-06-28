package com.exam.kudu_jdbc_api.service;

import com.exam.kudu_jdbc_api.model.Customer;

import java.util.List;

public interface CustomerService {

    void insertCustomer(Customer customer);
    boolean insertCustomers(List<Customer> customers);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer custid);
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer custId);
}
