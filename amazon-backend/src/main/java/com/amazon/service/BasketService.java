package com.amazon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.config.JwtRequestFilter;
import com.amazon.dao.BasketDao;
import com.amazon.dao.ProductDao;
import com.amazon.dao.UserDao;
import com.amazon.entity.Basket;
import com.amazon.entity.Product;
import com.amazon.entity.User;

@Service
public class BasketService {
	
	@Autowired
	private BasketDao basketDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	public void deleteBasketItem(Integer basketId) {
		basketDao.deleteById(basketId);
	}
	
	public Basket addToBasket(Integer productId) {
		
		Product product = productDao.findById(productId).get();
		
		String username = JwtRequestFilter.CURRENT_USER;
		
		User user = null;
		
		if(username != null) {
			user = userDao.findById(username).get();
			
		}
		
		List<Basket> basketList = basketDao.findByUser(user);
		List<Basket> filteredList = basketList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
		
		if(filteredList.size() > 0) {
			return null;
		}
		
		
		if(product != null && user != null) {
			Basket basket = new Basket(product, user);
			return basketDao.save(basket);
		}
		return null;
	}
	
	public List<Basket> getBasketDetails(){
		String username = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(username).get();
		return basketDao.findByUser(user);
		
	}
	
	

}
