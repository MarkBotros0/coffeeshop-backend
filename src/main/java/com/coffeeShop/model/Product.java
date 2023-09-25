package com.coffeeShop.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "products")
@NoArgsConstructor
@Data
public abstract class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	@NotBlank(message = "Product name is required")
	private String name;

	@Column(name = "AVAILABLE_FROM")
	@NotNull(message = "Product Available From Date is required")
	private LocalDate availableFrom;

	@Column(name = "AVAILABLE_TO")
	@NotNull(message = "Product Available To Date is required")
	private LocalDate availableTo;

	@Column(name = "url")
	private String url;

	@Column(name = "price")
	private float price;

	public Product(String name, Float price, String availableFrom, String availableTo, String url) {
		super();
		this.name = name;
		this.price = price;
		LocalDate parsedDateFrom = LocalDate.parse(availableFrom, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate parsedDateTo = LocalDate.parse(availableTo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		setAvailableFrom(parsedDateFrom);
		setAvailableTo(parsedDateTo);
		this.url = url;

	}

}
