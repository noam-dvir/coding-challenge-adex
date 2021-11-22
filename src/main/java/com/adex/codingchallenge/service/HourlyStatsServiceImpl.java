package com.adex.codingchallenge.service;

import java.util.List;
import java.util.Optional;
import com.adex.codingchallenge.exception.InvalidRequestException;
import com.adex.codingchallenge.model.HourlyStats;
import com.adex.codingchallenge.repository.HourlyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HourlyStatsServiceImpl implements HourlyStatsService{

    @Autowired
    private HourlyStatsRepository hourlyStatsRepository;

    @Override
    public HourlyStats getTotalDailyRequests(String date) {
        int validRequests = Optional.ofNullable(hourlyStatsRepository.getTotalDailyValidRequests(date)).orElse(0);
        int invalidRequests = Optional.ofNullable(hourlyStatsRepository.getTotalDailyInvalidRequests(date)).orElse(0);
        HourlyStats result = InitStatsEntry(-1, date, -1, validRequests, invalidRequests); //-1 for all hours and customers
        return result;
    }

    @Override
    public HourlyStats getTotalHourlyRequestsForAllCustomers(String date, int hour) {
        int validRequests = Optional.ofNullable(hourlyStatsRepository.getTotalhourlyValidRequestsForAllCustomers(date, hour)).orElse(0);
        int invalidRequests = Optional.ofNullable(hourlyStatsRepository.getTotalhourlyInvalidRequestsForAllCustomers(date, hour)).orElse(0);
        HourlyStats result = InitStatsEntry(-1, date, hour, validRequests, invalidRequests); //-1 for all customers
        return result;
    }


    @Override
    public HourlyStats getDailyCustomerRequests(int customerId, String date) {
        int validRequests = Optional.ofNullable(hourlyStatsRepository.countDailyValidCustomerRequests(customerId, date)).orElse(0);
        int invalidRequests = Optional.ofNullable(hourlyStatsRepository.countDailyInvalidCustomerRequests(customerId, date)).orElse(0);
        HourlyStats result = InitStatsEntry(customerId, date, -1, validRequests, invalidRequests); //-1 for all hours
        return result;
    }

    @Override
    public HourlyStats getHourlyCustomerRequests(int customerId, String date, int hour) {
        List<HourlyStats> customerHourStats = hourlyStatsRepository.getRequestsForCustomerForHour(customerId, date, hour);
        if (customerHourStats == null || customerHourStats.size() == 0){
            return null;
        }
        else if(customerHourStats.size() > 1){
            throw new InvalidRequestException("DB error: more than 1 entry for customer id "+customerId+" in "+date+" at "+hour+":00");
        }
        //found 1 hourly stats for customer in given date and time
        return customerHourStats.get(0);
    }

    @Override
    public void incrementValidRequests(int customerId, long timestamp) {
        String[] dateAndHour = TimeConverter.GetDateAndHourFromTimestamp(timestamp);
        String date = dateAndHour[0];
        int hour = Integer.parseInt(dateAndHour[1]);
        HourlyStats existingStats = getHourlyCustomerRequests(customerId, date, hour);
        if (existingStats == null){ //create new stats entry for customer in hour
            HourlyStats newEntry = InitStatsEntry(customerId, date, hour, 1, 0);
            hourlyStatsRepository.save(newEntry);
            return;
            
        }
        else{ //increment valid requests and update existing entry
            existingStats.setRequestCount(existingStats.getRequestCount() + 1);
            hourlyStatsRepository.save(existingStats);
            return;
        }        
    }

    @Override
    public void incrementInvalidRequests(int customerId, long timestamp) {
        String[] dateAndHour = TimeConverter.GetDateAndHourFromTimestamp(timestamp);
        String date = dateAndHour[0];
        int hour = Integer.parseInt(dateAndHour[1]);
        HourlyStats existingStats = getHourlyCustomerRequests(customerId, date, hour);
        if (existingStats == null){ //create new stats entry for customer in hour
            HourlyStats newEntry = InitStatsEntry(customerId, date, hour, 0, 1);
            hourlyStatsRepository.save(newEntry);
            return;
        }
        else{ //increment invalid requests and update existing
            existingStats.setInvalidCount(existingStats.getInvalidCount() + 1);
            hourlyStatsRepository.save(existingStats);
            return;
        }   
        
    }
    
    private HourlyStats InitStatsEntry(int _customerId, String _date, int _hour, int _validRequests, int _invalidRequests){
        HourlyStats stats = new HourlyStats();
        stats.setCustomerId(_customerId);
        stats.setDate(_date);
        stats.setHour(_hour);
        stats.setRequestCount(_validRequests);
        stats.setInvalidCount(_invalidRequests);
        return stats;
    }
}
