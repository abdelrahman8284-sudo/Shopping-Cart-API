package com.abdelrahman.shoppingcart.models;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "carts")
@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval=true)
	private Set<CartItem> items = new LinkedHashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false,unique = true)
	private User user;
	
	public void addItem(CartItem item) {
		items.add(item);
		item.setCart(this);
		calcTotalAmount();
	}
	
	public void removeItem(CartItem item) {
		this.items.remove(item);
		item.setCart(null);
		calcTotalAmount();
	}
	
	@PrePersist
	@PreUpdate
	public void calcTotalAmount() {
		// الاول محتاج اصفر ال totalAmount عشان لو فيه حساب ميتضفش عليها 
		this.totalAmount = BigDecimal.ZERO;
		// التأكد ان ال items مش ب null 
		if(items!=null) {
			for (CartItem item : items) {
				item.calculatePrices();
				if (item.getTotalPrice() != null) {
	                this.totalAmount = this.totalAmount.add(item.getTotalPrice());
	            }
			}
		}
	}
}
