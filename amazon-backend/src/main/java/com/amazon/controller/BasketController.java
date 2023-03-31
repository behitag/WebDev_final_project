package com.amazon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.entity.Basket;
import com.amazon.service.BasketService;

@RestController
public class BasketController {
	
	@Autowired
	private BasketService basketService;
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/addToBasket/{productId}"})
	public Basket addTobasket(@PathVariable(name="productId") Integer productId) {
		return basketService.addToBasket(productId);
		
	}
	
	@DeleteMapping({"/deleteBasketItem/{basketId}"})
	public void deleteBasketItem(@PathVariable(name= "basketId") Integer basketId) {
		basketService.deleteBasketItem(basketId);
	}
	
	
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/getBasketDetails"})
	public List<Basket> getBasketDetails() {
		return basketService.getBasketDetails();
		
	}
	

}
