package com.coffeeShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goodies")
@NoArgsConstructor
public class Goodie extends Product {
	@Column(name = "QUANTITY_LEFT")
	public int quantityLeft = 50;

	
	public Goodie(String name, Float price, String availableFrom, String availableTo, String url) {
		super(name, price, availableFrom, availableTo, url);
	}

	public int getQuantityLeft() {
		return quantityLeft;
	}

	public void setQuantityLeft(int quantityLeft) {
		this.quantityLeft = quantityLeft;
	}

	public String toString() {
		return String.format("%-13s |%-30s |%-4s |%-15s |%-15s|", "Goodies ", super.getName(), super.getId(),
				super.getAvailableFrom(), super.getAvailableTo());
	}

	public void decrement(int quantity) {
		this.quantityLeft = this.quantityLeft - quantity;
	}

	public void increment(int quantity) {
		this.quantityLeft = this.quantityLeft + quantity;
	}
}
