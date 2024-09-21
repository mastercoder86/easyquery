package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	//Customer findByEmail(String name);
	//Customer findByEmailAndPassword(String name,String password);
	Customer findByPhone(String phone);
	
}
