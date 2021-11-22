package com.adex.codingchallenge.service;

import java.util.List;
import java.util.Optional;
import com.adex.codingchallenge.exception.InvalidRequestException;
import com.adex.codingchallenge.model.Customer;
import com.adex.codingchallenge.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> customerItem = this.customerRepository.findById(customer.getId());
        if (customerItem.isPresent()){
            Customer customerUpdate = customerItem.get();
            customerUpdate.setId(customer.getId());
            customerUpdate.setName(customer.getName());
            customerUpdate.setIsActive(customer.getIsActive());
            customerRepository.save(customerUpdate);
            return customerUpdate;
        }
        else{
            throw new InvalidRequestException("Custoner with id "+customer.getId()+" not found");
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Optional<Customer> customerItem = this.customerRepository.findById(customerId);
        if (customerItem.isPresent()){
            return customerItem.get();
        }
        else{
            throw new InvalidRequestException("Custoner with id "+customerId+" not found");
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        Optional<Customer> customerItem = this.customerRepository.findById(customerID);
        if (customerItem.isPresent()){
            this.customerRepository.delete(customerItem.get());
        }
        else{
            throw new InvalidRequestException("Custoner with id "+customerID+" not found");
        }        
    }
    
}
