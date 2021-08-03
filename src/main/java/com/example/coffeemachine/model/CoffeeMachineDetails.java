package com.example.coffeemachine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Contains the coffee machine details provided in json input
 */
@Getter
public class CoffeeMachineDetails {

	private final Machine machine;

	@JsonCreator
	public CoffeeMachineDetails(@JsonProperty("machine") Machine machine) {
		this.machine = machine;
	}
}
