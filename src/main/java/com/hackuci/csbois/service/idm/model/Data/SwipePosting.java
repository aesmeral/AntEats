package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SwipePosting {
    @JsonProperty("name")
    String name;
    @JsonProperty("availability")
    String availability;

    @JsonCreator
    public SwipePosting(String name, String availability) {
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getAvailability() {
        return availability;
    }
}
