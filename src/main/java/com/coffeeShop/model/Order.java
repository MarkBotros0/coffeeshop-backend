package com.coffeeShop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private int orderId;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@ElementCollection(fetch = FetchType.EAGER)
	@Embedded
	@CollectionTable(name = "order_items", joinColumns = { @JoinColumn(name = "ORDER_ID") })
	@NotNull
	private List<OrderItem> orderItems = new ArrayList<>();

	@Column(name = "total_price")
	private Float totalPrice = 0f;

	@ManyToOne
	@JsonIgnore
	private User user;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", createdAt=" + createdAt + ", totalPrice=" + totalPrice + "]";
	}

	public void addOrderItem(Product product, int quantity) {
		OrderItem item = new OrderItem(product, quantity);
		orderItems.add(item);

		float price = quantity * product.getPrice();
		setTotalPrice(Math.round((getTotalPrice() + price) * 100.0f) / 100.0f);
	}

	public void addOrderItem(OrderItem orderItem) {

		orderItems.add(orderItem);

		float price = orderItem.getQuantity() * orderItem.getProduct().getPrice();
		setTotalPrice(Math.round((getTotalPrice() + price) * 100.0f) / 100.0f);
	}

}