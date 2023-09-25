package com.coffeeShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coffeebeans")
@NoArgsConstructor
@Getter
@Setter
public class CoffeeBean extends Product {
	@Column(name = "QUANTITY_LEFT")
	public int quantityLeft = 50;

	public CoffeeBean(String name, Float price, String availableFrom, String availableTo, String url) {
		super(name, price, availableFrom, availableTo, url);
	}

	public String toString() {
		return String.format("%-13s |%-30s |%-4s |%-15s |%-15s|", "Coffee Beans ", super.getName(), super.getId(),
				super.getAvailableFrom(), super.getAvailableTo());
	}

	public void decrement(int quantity) {
		this.quantityLeft = this.quantityLeft - quantity;
	}

	public void increment(int quantity) {
		this.quantityLeft = this.quantityLeft + quantity;
	}
}
