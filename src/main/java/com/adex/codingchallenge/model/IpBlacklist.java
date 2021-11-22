package com.adex.codingchallenge.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ip_blacklist")
public class IpBlacklist {
    
    @Id
    @Column(name = "ip")
    private String ip;

    //getters
    public String getIp(){
        return ip;
    }

    //setters
    public void setIp(String _ip){
        this.ip = _ip;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof IpBlacklist)) return false;
        IpBlacklist entry = (IpBlacklist) o;         
        return Objects.equals(ip, entry.ip);
    }
}