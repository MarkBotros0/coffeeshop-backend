package com.coffeeShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drinks")
@NoArgsConstructor
public class Drink extends Product {

	public Drink(String name, Float price, String availableFrom, String availableTo, String url) {
		super(name, price, availableFrom, availableTo, url);
	}

	public String toString() {
		return String.format("%-13s |%-30s |%-4s |%-15s |%-15s|", "Drinks ", super.getName(), super.getId(),
				super.getAvailableFrom(), super.getAvailableTo());
	}

	public boolean equals(Object o) {
		if (!(o instanceof Product)) {
			return false;
		}
		Product otherProduct = (Product) o;
		return otherProduct.getId() == this.getId();
	}
}
