package com.exam.kudu_jdbc_api.service;

import com.exam.kudu_jdbc_api.dao.CustomerDao;
import com.exam.kudu_jdbc_api.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerDao customerDao;

    @Override
    public void insertCustomer(Customer customer) {
        customerDao.insertCustomer(customer);
    }

    @Override
    public boolean insertCustomers(List<Customer> customers) {
        return customerDao.insertCustomers(customers);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(Integer custid) {
        Customer customer = customerDao.getCustomerById(custid);
        System.out.println(customer);
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Integer custId) {
        customerDao.deleteCustomer(custId);
    }
}
