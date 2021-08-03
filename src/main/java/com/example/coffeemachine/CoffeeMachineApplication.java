package com.example.coffeemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeMachineApplication.class, args);

		CoffeeMachine coffeeMachine = new CoffeeMachine("coffee-machine.json");

		coffeeMachine.requestBeverage("hot_tea");
		coffeeMachine.requestBeverage("hot_coffee");
		coffeeMachine.requestBeverage("black_tea");
		coffeeMachine.requestBeverage("green_tea");

		coffeeMachine.close();
	}

}
