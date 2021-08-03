package com.example.coffeemachine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Outlet {
	private int count;

	@JsonCreator
	public Outlet(@JsonProperty("count_n") int count) {
		this.count = count;
	}
}
