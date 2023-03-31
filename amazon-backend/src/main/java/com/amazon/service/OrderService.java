package com.amazon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.config.JwtRequestFilter;
import com.amazon.dao.BasketDao;
import com.amazon.dao.OrderDao;
import com.amazon.dao.ProductDao;
import com.amazon.dao.UserDao;
import com.amazon.entity.Basket;
import com.amazon.entity.OrderDetail;
import com.amazon.entity.OrderInput;
import com.amazon.entity.ProductQuantity;
import com.amazon.entity.Product;
import com.amazon.entity.User;

@Service
public class OrderService {
	
	private static final String ORDER_PLACED = "Placed";  
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BasketDao basketDao;
	
	public List<OrderDetail> getAllOrderDetails(){
		List<OrderDetail> orderDetails = new ArrayList<>();
		orderDao.findAll().forEach(e -> orderDetails.add(e));
		
		return orderDetails;
	}
	
	public List<OrderDetail> getOrderDetails() {
		String currentUser = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(currentUser).get();
		
		return orderDao.findByUser(user);
	}
	
	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<ProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
		
		for(ProductQuantity o: productQuantityList) {
			Product product = productDao.findById(o.getProductId()).get();
			
			String currentUser = JwtRequestFilter.CURRENT_USER;
			User user= userDao.findById(currentUser).get();
			
			OrderDetail orderDetail = new OrderDetail(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice()*o.getQuantity(),
					product,
					user);
			
			if(!isSingleProductCheckout) {
				List<Basket> baskets = basketDao.findByUser(user);
				baskets.stream().forEach(x -> basketDao.deleteById(x.getBasketId()));
				
			}
			orderDao.save(orderDetail);
		}
	}
	
	

}
