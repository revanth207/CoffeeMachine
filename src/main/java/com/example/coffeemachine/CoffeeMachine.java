package com.example.coffeemachine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;

import com.example.coffeemachine.ingredients.IngredientsManager;
import com.example.coffeemachine.model.CoffeeMachineDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Coffee Machine to process beverage requests and refill ingredients
 */
@Slf4j
public class CoffeeMachine {

	private HashMap<String, HashMap<String, Integer>> beverages;
	private final IngredientsManager ingredientsManager = new IngredientsManager();
	private ExecutorService executorService;

	/**
	 * Initializes the beverages map, ingredients manager and thread pool service
	 *
	 * @param fileName - coffee-machine json
	 */
	public CoffeeMachine(String fileName) {
		try {
			String jsonInput = FileUtils.readFileToString(
					new File(Objects.requireNonNull(CoffeeMachine.class.getClassLoader().getResource(fileName)).getFile()),
					StandardCharsets.UTF_8);
			CoffeeMachineDetails machineDetails = new ObjectMapper().readValue(jsonInput, CoffeeMachineDetails.class);
			initializeCoffeeMachine(machineDetails);

		} catch (IOException e) {
			log.error("Invalid coffee machine details", e);
			e.printStackTrace();
		}
	}

	private void initializeCoffeeMachine(CoffeeMachineDetails machineDetails) {
		this.beverages = machineDetails.getMachine().getBeverages();
		this.executorService = Executors.newFixedThreadPool(machineDetails.getMachine().getOutlets().getCount());

		machineDetails.getMachine().getIngredientQuantityMap().forEach(ingredientsManager::addQuantity);
	}

	/**
	 * checks if the given beverage can be prepared and prepares the beverage
	 *
	 * @param beverage - beverage name
	 */
	public void requestBeverage(String beverage) {
		executorService.execute(() -> prepareBeverage(beverage));
	}

	public void refillIngredient(String ingredient, Integer quantity) {
		log.info("Refill request: ingredient: {}, quantity: {}", ingredient, quantity);
		synchronized (ingredientsManager) {
			ingredientsManager.addQuantity(ingredient, quantity);
		}
	}

	private void prepareBeverage(String beverage) {
		HashMap<String, Integer> beverageRecipe = beverages.getOrDefault(beverage, null);

		if (beverageRecipe == null) {
			log.warn("Currently, {} is not available", beverage);
			return;
		}

		synchronized (ingredientsManager) {
			/*
			 * checks if the required ingredients for beverage are available in ingredients manager
			 */
			for (Map.Entry<String, Integer> entry : beverageRecipe.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				Integer availableQuantity = ingredientsManager.getAvailableQuantity(key);

				if (availableQuantity == 0) {
					log.warn("{} cannot be prepared because {} is not available", beverage, key);
					return;
				}

				if (availableQuantity < value) {
					log.warn("{} cannot be prepared because {} is not sufficient", beverage, key);
					return;
				}
			}

			/*
			 * updates the ingredients manager after beverage preparation
			 */
			beverageRecipe.forEach((ingredient, quantity) -> {
				Integer availableQuantity = ingredientsManager.getAvailableQuantity(ingredient);
				ingredientsManager.updateQuantity(ingredient, availableQuantity - quantity);
			});

			log.info("{} is prepared", beverage);
		}
	}


	/**
	 * To shutdown coffee machine
	 */
	public void close() {
		executorService.shutdown();
	}
}
