package com.abdelrahman.shoppingcart.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cart_items")
@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantity;
	
	private BigDecimal unitPrice;
	
	private BigDecimal totalPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@ManyToOne(fetch = FetchType.LAZY)
	
	@JoinColumn(name="product_id")
	private Product product;
	
	@PrePersist
	@PreUpdate
	public void calculatePrices() {
		if(product!=null && unitPrice ==null) {
			this.unitPrice = product.getPrice();
		}
		if(unitPrice!=null) {
			this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
			
		}
	}
}
