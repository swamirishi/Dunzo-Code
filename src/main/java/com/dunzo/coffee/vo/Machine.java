package com.dunzo.coffee.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "machine")
public class Machine {
    @JsonProperty("outlets")
    private final Outlet outlet;
    @JsonProperty("total_items_quantity")
    private final Map<String, Integer> items;
    @JsonProperty("beverages")
    private final Map<String, Map<String, Integer>> beverages;
    
    public Machine(@JsonProperty("outlets") Outlet outlet,
                   @JsonProperty("total_items_quantity") Map<String, Integer> items,
                   @JsonProperty("beverages") Map<String, Map<String, Integer>> beverages) {
        this.outlet = outlet;
        this.items = items;
        this.beverages = beverages;
    }
    
    public Outlet getOutlet() {
        return outlet;
    }
    
    public Map<String, Integer> getItems() {
        return items;
    }
    
    public Map<String, Map<String, Integer>> getBeverages() {
        return beverages;
    }
}
