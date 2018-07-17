package com.technaxis.sample.livetyping.data.drink_categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;

import java.util.List;

public class DrinkCategoriesResponse {

    @JsonProperty("drinks")
    private List<DrinkCategory> categories;

    public List<DrinkCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<DrinkCategory> categories) {
        this.categories = categories;
    }
}
