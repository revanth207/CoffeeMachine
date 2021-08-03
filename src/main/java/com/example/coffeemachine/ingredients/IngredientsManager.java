package com.example.coffeemachine.ingredients;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * IngredientsManager to get, add and update ingredients
 * Warns when the ingredient quantity is low
 */
@Slf4j
public class IngredientsManager {

	private static final Integer MAX_ALLOWED_QUANTITY = 1000;
	private static final Integer MIN_ALLOWED_QUANTITY = 100;
	private final HashMap<String, Integer> inventory = new HashMap<>();

	public Integer getAvailableQuantity(String ingredient) {
		return inventory.getOrDefault(ingredient, 0);
	}

	public void addQuantity(String ingredient, Integer quantity) {
		Integer currentQuantity = getAvailableQuantity(ingredient);
		if (currentQuantity + quantity > MAX_ALLOWED_QUANTITY) {
			log.warn("Discarding the excess. Quantity added was more than the maximum allowed: {}", MAX_ALLOWED_QUANTITY);
			inventory.put(ingredient, MAX_ALLOWED_QUANTITY);
		} else {
			inventory.put(ingredient, currentQuantity + quantity);
		}
	}

	public void updateQuantity(String ingredient, Integer quantity) {
		if (quantity < MIN_ALLOWED_QUANTITY) {
			log.warn("{} is running low in quantity. Please refill", ingredient);
		}
		inventory.put(ingredient, quantity);
	}
}
