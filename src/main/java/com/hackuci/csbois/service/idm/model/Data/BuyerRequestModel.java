package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackuci.csbois.service.idm.model.RequestModel;

public class BuyerRequestModel extends RequestModel {
    @JsonProperty(value = "swipe_id",required = true)
    private String swipe_id;


    public BuyerRequestModel(@JsonProperty(value = "email",required = true) String email,
                             @JsonProperty(value = "swipe_id",required = true) String swipe_id)
    {
        super(email);
        this.swipe_id = swipe_id;
    }

    public String getSwipe_id() {
        return swipe_id;
    }
}
