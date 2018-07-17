package com.technaxis.sample.livetyping.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Drink implements Serializable {

    @JsonProperty("idDrink")
    private Long id;

    @JsonProperty("strDrink")
    private String name;

    @JsonProperty("strDrinkThumb")
    private String imageUrl;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
