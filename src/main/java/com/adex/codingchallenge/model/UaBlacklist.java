package com.adex.codingchallenge.model; 

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ua_blacklist")
public class UaBlacklist {
    
    @Id
    @Column(name = "ua")
    private String ua;

    //getters
    public String getUa(){
        return ua;
    }

    //setters
    public void setUa(String _ua){
        this.ua = _ua;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UaBlacklist)) return false;
        UaBlacklist entry = (UaBlacklist) o;         
        return Objects.equals(ua, entry.ua);
    }
}
