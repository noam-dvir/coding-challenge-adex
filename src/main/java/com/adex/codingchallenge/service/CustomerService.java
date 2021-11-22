package com.adex.codingchallenge.service;

import java.util.List;
import com.adex.codingchallenge.model.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int customerId);
    
    void deleteCustomer(int customerID);
}
