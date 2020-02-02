package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SwipePosting {
    @JsonProperty("name")
    String name;
    @JsonProperty("availability")
    String availability;
    @JsonProperty("cost")
    float cost;
    @JsonCreator
    public SwipePosting(String name, String availability,float cost) {
        this.name = name;
        this.availability = availability;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }

    public float getCost() {
        return cost;
    }
}
