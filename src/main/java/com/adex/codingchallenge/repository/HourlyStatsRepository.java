package com.adex.codingchallenge.repository;

import java.util.List;
import com.adex.codingchallenge.model.HourlyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HourlyStatsRepository extends JpaRepository<HourlyStats, Integer>{
    
    @Query(value="select * from hourly_stats a where a.customer_id= :_customerId and a.date= :_date and a.hour= :_hour", 
    nativeQuery=true)
    List<HourlyStats> getRequestsForCustomerForHour(int _customerId, String _date, int _hour);

    @Query(value="SELECT SUM (request_count) FROM hourly_stats WHERE date = :date and customer_id = :customerId", 
    nativeQuery=true)
    Integer countDailyValidCustomerRequests(int customerId, String date);

    @Query(value="SELECT SUM (invalid_count) FROM hourly_stats WHERE date = :date and customer_id = :customerId", 
    nativeQuery=true)
    Integer countDailyInvalidCustomerRequests(int customerId, String date);

    @Query(value="SELECT SUM (request_count) FROM hourly_stats WHERE date = :date", 
    nativeQuery=true)
    Integer getTotalDailyValidRequests(String date);

    @Query(value="SELECT SUM (invalid_count) FROM hourly_stats WHERE date = :date", 
    nativeQuery=true)
    Integer getTotalDailyInvalidRequests(String date);

    @Query(value="SELECT SUM (request_count) FROM hourly_stats WHERE date = :date and hour = :hour", 
    nativeQuery=true)
    Integer getTotalhourlyValidRequestsForAllCustomers(String date, int hour);

    @Query(value="SELECT SUM (invalid_count) FROM hourly_stats WHERE date = :date and hour = :hour", 
    nativeQuery=true)
    Integer getTotalhourlyInvalidRequestsForAllCustomers(String date, int hour);
    
}
