package com.adex.codingchallenge.controller;

import java.util.HashMap;
import java.util.Map;
import com.adex.codingchallenge.model.HourlyStats;
import com.adex.codingchallenge.service.HourlyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private HourlyStatsService hourlyStatsService;

    //get total requests from all Customers for a specific day
    @GetMapping("/stats/all/{date}")
    public ResponseEntity<?> getAllDailyRequests(@PathVariable("date") String date){
        HourlyStats totalDailyRequests = hourlyStatsService.getTotalDailyRequests(date);
        Map<String, Integer> resultsMap = InitResultsMap(totalDailyRequests.getRequestCount(), totalDailyRequests.getInvalidCount());
        return ResponseEntity.ok().body(resultsMap);
    }

    //get total requests from all customers for a specific hour
    @GetMapping("/stats/all/{date}/{hour}")
    public ResponseEntity<?> getHourlyRequestsForAllCustomers(@PathVariable("date") String date, @PathVariable("hour") String hourInput){
        int hour = -1;
        try{
            hour = Integer.parseInt(hourInput);
        }
        catch (NumberFormatException ex){
            return ResponseEntity.badRequest().body("incorrect input: /"+hourInput);
        }
        HourlyStats totalHourlyRequests = hourlyStatsService.getTotalHourlyRequestsForAllCustomers(date, hour);
        Map<String, Integer> resultsMap = InitResultsMap(totalHourlyRequests.getRequestCount(), totalHourlyRequests.getInvalidCount());
        return ResponseEntity.ok().body(resultsMap);
    }

    //get total daily requests for a specific customer
    @GetMapping("/stats/customer/{customer_id}/{date}")
    public ResponseEntity<?> getTotalDailyRequestsForCustomer(@PathVariable("customer_id") String customer_id_input, @PathVariable("date") String date){
        int customer_id = -1;
        try{
            customer_id = Integer.parseInt(customer_id_input);
        }
        catch (NumberFormatException ex){
            return ResponseEntity.badRequest().body("incorrect input: /"+customer_id_input);
        }
        HourlyStats dailyCustomerRequests = hourlyStatsService.getDailyCustomerRequests(customer_id, date);
        Map<String, Integer> resultsMap = InitResultsMap(dailyCustomerRequests.getRequestCount(), dailyCustomerRequests.getInvalidCount());
        return ResponseEntity.ok().body(resultsMap);
    }

    //get requests from a specific customer on a specific hour
    @GetMapping("/stats/customer/{customer_id}/{date}/{hour}")
    public ResponseEntity<?> getTotalHourlyCustomerRequests(@PathVariable("customer_id") String customer_id_input, @PathVariable("date") String date, @PathVariable("hour") String hourInput){
        int customer_id = -1;
        try{
            customer_id = Integer.parseInt(customer_id_input);
        }
        catch (NumberFormatException ex){
            return ResponseEntity.badRequest().body("incorrect customer id input: /"+customer_id_input);
        }
        int hour = -1;
        try{
            hour = Integer.parseInt(hourInput);
        }
        catch (NumberFormatException ex){
            return ResponseEntity.badRequest().body("incorrect hour input: /"+hourInput);
        }
        HourlyStats hourlyCustomerRequests = hourlyStatsService.getHourlyCustomerRequests(customer_id, date, hour);
        int validRequests = (hourlyCustomerRequests == null) ? 0 : hourlyCustomerRequests.getRequestCount();
        int invalidRequests = (hourlyCustomerRequests == null) ? 0 : hourlyCustomerRequests.getInvalidCount();

        Map<String, Integer> resultsMap = InitResultsMap(validRequests, invalidRequests);
        return ResponseEntity.ok().body(resultsMap);
    }

    private static Map<String, Integer> InitResultsMap(int validRequests, int invalidRequests){
        Map<String, Integer> resultsMap = new HashMap<String, Integer>();
        resultsMap.put("valid requests", validRequests);
        resultsMap.put("invalid requests", invalidRequests);
        return resultsMap;
    }

}
