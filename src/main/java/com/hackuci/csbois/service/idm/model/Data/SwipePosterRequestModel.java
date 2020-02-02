package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackuci.csbois.service.idm.model.RequestModel;


public class SwipePosterRequestModel extends RequestModel {
    @JsonProperty("availability")
    String availability;
    @JsonProperty(value = "cost", required = true)
    float cost;

    @JsonCreator
    public SwipePosterRequestModel(@JsonProperty(value = "email", required = true) String email,
                                   @JsonProperty(value = "availability") String availability,
                                   @JsonProperty(value = "cost",required = true) float cost) {
        super(email);
        this.availability = availability;
        this.cost = cost;
    }

    @JsonProperty("availability")
    public String getAvailability() {
        return availability;
    }

    @JsonProperty("cost")
    public float getCost() {
        return cost;
    }
}
