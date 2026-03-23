package com.abdelrahman.shoppingcart.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="categories")
@Builder@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products= new ArrayList<>();

	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	
}
