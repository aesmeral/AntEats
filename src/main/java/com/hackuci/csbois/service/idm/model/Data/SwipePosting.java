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
    @JsonProperty("swipe_id")
    String swipe_id;
    @JsonCreator
    public SwipePosting(String name, String availability,float cost,String swipe_id) {
        this.name = name;
        this.availability = availability;
        this.cost = cost;
        this.swipe_id = swipe_id;
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

    public String getSwipe_id() {
        return swipe_id;
    }
}
