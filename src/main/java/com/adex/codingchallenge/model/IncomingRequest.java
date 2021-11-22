package com.adex.codingchallenge.model;

public class IncomingRequest {
    private int customerID;
    private long tagID;
    private String userID;
    private String remoteIP;
    private long timestamp;

    //getters
    public int getCustomerId(){
        return this.customerID;
    }
    public long getTagId(){
        return this.tagID;
    }
    public String getUserId(){
        return this.userID;
    }
    public String getRemoteIp(){
        return this.remoteIP;
    }
    public long getTimestamp(){
        return this.timestamp;
    }

    //setters
    public void setTimestamp(long _timestamp){
        this.timestamp = _timestamp;
    }       
    public void setUserID(String _userId){
        this.userID = _userId;
    }    
    public void setRemoteIP(String _remoteIp){
        this.remoteIP = _remoteIp;
    }
    public void setTagID(int _tagId){
        this.tagID = _tagId;
    }    
    public void setCustomerID(int _customerId){
        this.customerID = _customerId;
    }    
}
