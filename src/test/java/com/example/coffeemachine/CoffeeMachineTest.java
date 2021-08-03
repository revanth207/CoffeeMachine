package com.example.coffeemachine;

import org.junit.jupiter.api.Test;

class CoffeeMachineTest {

	@Test
	void testInitialCoffeeMachine() {

		CoffeeMachine coffeeMachine = new CoffeeMachine("coffee-machine.json");

		// hot_tea and hot_coffee are prepared
		coffeeMachine.requestBeverage("hot_tea");
		coffeeMachine.requestBeverage("hot_coffee");
		coffeeMachine.requestBeverage("black_tea");
		coffeeMachine.requestBeverage("green_tea");

		//add green_mixture ingredient
		coffeeMachine.refillIngredient("green_mixture", 100);
		coffeeMachine.requestBeverage("green_tea"); //prepared

		coffeeMachine.close();
	}

	@Test
	void testCoffeeMachineRefill() {

		CoffeeMachine coffeeMachine = new CoffeeMachine("coffee-machine.json");

		//add green_mixture ingredient
		coffeeMachine.refillIngredient("green_mixture", 100);
		coffeeMachine.refillIngredient("sugar_syrup", 1000);
		coffeeMachine.refillIngredient("hot_water", 1000);

		// all are prepared
		coffeeMachine.requestBeverage("hot_tea");
		coffeeMachine.requestBeverage("hot_coffee");
		coffeeMachine.requestBeverage("black_tea");
		coffeeMachine.requestBeverage("green_tea");

		coffeeMachine.close();
	}
}
