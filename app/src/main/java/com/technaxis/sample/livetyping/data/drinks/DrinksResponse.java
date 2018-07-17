package com.technaxis.sample.livetyping.data.drinks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technaxis.sample.livetyping.business.model.Drink;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;

import java.util.List;

public class DrinksResponse {

    @JsonProperty("drinks")
    private List<Drink> drinks;

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }
}
