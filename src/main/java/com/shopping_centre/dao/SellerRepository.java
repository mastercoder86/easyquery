package com.shopping_centre.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findByEmail(String name);

	Seller findByEmailAndPassword(String name, String password);

}
