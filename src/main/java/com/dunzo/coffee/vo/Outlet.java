package com.dunzo.coffee.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Outlet {
    @JsonProperty("count_n")
    int numberOfOutlets;
    
    public Outlet(@JsonProperty("count_n")int numberOfOutlets) {
        this.numberOfOutlets = numberOfOutlets;
    }
    
    public int getNumberOfOutlets() {
        return numberOfOutlets;
    }
}
