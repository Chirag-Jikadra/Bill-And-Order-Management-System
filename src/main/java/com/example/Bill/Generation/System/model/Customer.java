package com.example.Bill.Generation.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cId;

    private String customerName;
    private String modNo;

    public int getCid() {
        return cId;
    }

    public void setCid(int cId) {
        this.cId = cId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getModNo() {
        return modNo;
    }

    public void setModNo(String modNo) {
        this.modNo = modNo;
    }
}
