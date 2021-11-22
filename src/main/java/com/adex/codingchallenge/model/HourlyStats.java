package com.adex.codingchallenge.model; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hourly_stats")
public class HourlyStats 
{    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "request_count")
    private int requestCount;

    @Column(name = "invalid_count")
    private int invalidCount;

    @Column(name = "date")
    private String date;

    @Column(name = "hour")
    private int hour;

    //getters
    public int getId(){
        return id;
    }
    public int getCustomerId(){
        return customerId;
    }
    public int getRequestCount(){
        return requestCount;
    }
    public int getInvalidCount(){
        return invalidCount;
    }
    public String getDate(){
        return date;
    }
    public int getHour(){
        return hour;
    }

    //setters
    public void setId(int _id){
        this.id = _id;
    }
    public void setCustomerId(int _customerId){
        this.customerId = _customerId;
    }
    public void setRequestCount(int _requestCount){
        this.requestCount = _requestCount;
    }
    public void setInvalidCount(int _invalidCount){
        this.invalidCount = _invalidCount;
    }
    public void setDate(String _date){
        this.date = _date;
    }
    public void setHour(int _hour){
        this.hour = _hour;
    }
}
