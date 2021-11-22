package com.adex.codingchallenge.service;

import com.adex.codingchallenge.model.HourlyStats;

public interface HourlyStatsService {

    void incrementValidRequests(int customerId, long timestamp);

    void incrementInvalidRequests(int customerId, long timestamp);

    //total requests from all users for a specific day
    HourlyStats getTotalDailyRequests(String date); 

    //requests from all users on a specific hour
    public HourlyStats getTotalHourlyRequestsForAllCustomers(String date, int hour);

    //total number of total requests for a specific customer in a specific day
    HourlyStats getDailyCustomerRequests(int customerId, String date); 

    //number of requests for a specific customer in a specific hour in a specific day
    HourlyStats getHourlyCustomerRequests(int customerId, String date, int hour); 
    
}
