package com.exam.kudu_jdbc_api.dao;

import com.exam.kudu_jdbc_api.model.Customer;

import java.util.List;

public interface CustomerDao {

    void insertCustomer(Customer cus);
    boolean insertCustomers(List<Customer> employees);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer custid);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Integer custId);
}
