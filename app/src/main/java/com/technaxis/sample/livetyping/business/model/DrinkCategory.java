package com.technaxis.sample.livetyping.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DrinkCategory implements Serializable {

    @JsonProperty("strCategory")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
