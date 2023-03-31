package com.amazon.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazon.entity.Basket;
import com.amazon.entity.User;

@Repository
public interface BasketDao extends CrudRepository<Basket, Integer>{
	
	public List<Basket> findByUser(User user);

}
