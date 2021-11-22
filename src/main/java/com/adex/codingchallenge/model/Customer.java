package com.adex.codingchallenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private int isActive;

    //getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getIsActive(){
        return isActive;
    }

    //setters
    public void setId(int _id){
        this.id = _id;
    }
    public void setName(String _name){
        this.name = _name;
    }
    public void setIsActive(int _isActive){
        this.isActive = _isActive;
    }
}
