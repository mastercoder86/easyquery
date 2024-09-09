package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.MyOrder;

public interface MyOrderRepository extends JpaRepository<MyOrder, Integer> {
	public MyOrder findByOrderId(String orderId);
}
