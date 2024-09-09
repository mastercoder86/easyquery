package com.shopping_centre.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.shopping_centre.entities.Seller;
import com.shopping_centre.entities.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	//List<Service> findServicesBySeller(Seller seller);
	List<Service> findByCategory(String category);
}
