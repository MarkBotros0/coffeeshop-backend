package com.coffeeShop.model;

import com.coffeeShop.DTO.OrderItemDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	@ManyToOne 
	private Product product;
	
	@Column(name = "QUANTITY")
	private int quantity;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrderItem) {
			OrderItem otherItem = (OrderItem) obj;
			return product.getId() == otherItem.getProduct().getId() && quantity == otherItem.getQuantity();
		}
		return false;
	}
	
	public OrderItemDTO convertToLineItemDto() {
		OrderItemDTO lineItemDTO = new OrderItemDTO();
		lineItemDTO.setProductId(product.getId());
		lineItemDTO.setQuantity(quantity);
		return lineItemDTO;
	}
}