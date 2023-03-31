package com.amazon.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Basket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer basketId;
	@OneToOne
	private Product product;
	@OneToOne
	private User user;
	
	
	
	public Basket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Basket(Product product, User user) {
		super();
		this.product = product;
		this.user = user;
	}


	public Integer getBasketId() {
		return basketId;
	}
	public void setBasketId(Integer basketId) {
		this.basketId = basketId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
