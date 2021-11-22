package com.adex.codingchallenge.controller;

import java.util.List;
import com.adex.codingchallenge.model.Customer;
import com.adex.codingchallenge.model.IncomingRequest;
import com.adex.codingchallenge.model.IpBlacklist;
import com.adex.codingchallenge.model.UaBlacklist;
import com.adex.codingchallenge.service.CustomerService;
import com.adex.codingchallenge.service.HourlyStatsService;
import com.adex.codingchallenge.service.IpBlacklistService;
import com.adex.codingchallenge.service.UaBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private IpBlacklistService ipBlacklistService;

    @Autowired
    private UaBlacklistService uaBlacklistService;

    @Autowired
    private HourlyStatsService hourlyStatsService;

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id){
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok().body(this.customerService.createCustomer(customer));
    }

    @PostMapping("/customer")
    public ResponseEntity<?> handleRequest(@RequestBody IncomingRequest incomingRequest, @RequestHeader(value = "User-Agent") String userAgent){

        int customerId = incomingRequest.getCustomerId();
        long timestamp = incomingRequest.getTimestamp();
        //check fields
        if (customerId == 0){
            return ResponseEntity.badRequest().body("invalid or missing customerID field");
        }
        if (timestamp == 0){
            return ResponseEntity.badRequest().body("invalid or missing timestamp field");
        }
        if (incomingRequest.getTagId() == 0){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("invalid or missing tagID field");
        }        
        if (incomingRequest.getUserId() == null || incomingRequest.getUserId().length() == 0){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("invalid or missing userID field");
        }
        if (incomingRequest.getRemoteIp() == null || incomingRequest.getRemoteIp().length() == 0){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("invalid or missing remoteIP field");
        }

        //check customer id in DB 
        Customer customer = null;
        try {
            customer = customerService.getCustomerById(customerId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //check if customer is not active
        if (customer == null){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("customerID "+customerId+" not found");
        }
        if (customer.getIsActive() == 0){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("INACTIVE CUSTOMER, customerID: "+customerId+" , name: "+customer.getName());
        }

        //check if IP is blacklisted
        IpBlacklist requestIp = new IpBlacklist();
        requestIp.setIp(incomingRequest.getRemoteIp());
        if (ipBlacklistService.isIpBlacklisted(requestIp)){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("IP address "+incomingRequest.getRemoteIp()+" is blacklisted");
        }

        //check if UA is blacklisted
        UaBlacklist requestUaToCheck = new UaBlacklist();
        requestUaToCheck.setUa(userAgent);
        if (uaBlacklistService.isUaBlacklisted(requestUaToCheck)){
            hourlyStatsService.incrementInvalidRequests(customerId, timestamp);
            return ResponseEntity.badRequest().body("User Agent "+userAgent+" is blacklisted");
        }

        //request is valid
        hourlyStatsService.incrementValidRequests(customerId, timestamp);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        customer.setId(id);
        return ResponseEntity.ok().body(this.customerService.updateCustomer(customer));
    }
    
    @DeleteMapping("/customers/{id}")
    public HttpStatus deleteCustomer(@PathVariable int id){
        this.customerService.deleteCustomer(id);
        return HttpStatus.OK;
    }
}
