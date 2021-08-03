package com.example.coffeemachine.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Machine {

	private final Outlet outlets;


	private final HashMap<String, Integer> ingredientQuantityMap;


	private final HashMap<String, HashMap<String, Integer>> beverages;

	@JsonCreator
	public Machine(@JsonProperty("outlets") Outlet outlets, @JsonProperty("total_items_quantity") HashMap<String, Integer> ingredientQuantityMap,
				   @JsonProperty("beverages") HashMap<String, HashMap<String, Integer>> beverages) {
		this.outlets = outlets;
		this.ingredientQuantityMap = ingredientQuantityMap;
		this.beverages = beverages;
	}
}
