package com.hackuci.csbois.service.idm.model.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackuci.csbois.service.idm.base.Result;
import com.hackuci.csbois.service.idm.model.ResponseModel;

public class SwipeListResponseModel extends ResponseModel {
    @JsonProperty("swipes")
    SwipePosting [] swipes;

    public SwipeListResponseModel(Result result) {
        super(result);
    }

    public void setSwipes(SwipePosting[] swipes) {
        this.swipes = swipes;
    }

    public SwipePosting[] getSwipes() {
        return swipes;
    }
}
