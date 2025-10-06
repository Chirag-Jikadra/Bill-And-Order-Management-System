package com.example.Bill.Generation.System.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;


public class OrderRequest {
    @JsonProperty("customerId")
    private int cid; // must match JSON field name
    private Map<Integer, Integer> hashMap;

    // getters and setters
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Map<Integer, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(Map<Integer, Integer> hashMap) {
        this.hashMap = hashMap;
    }
}