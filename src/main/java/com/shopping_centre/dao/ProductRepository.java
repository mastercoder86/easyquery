package com.shopping_centre.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findProductsBySeller(Seller seller);

	List<Product> findByCategory(String category);

	@Query("select p from Product p where p.p_name LIKE :name% OR p.p_name LIKE %:name OR p.p_name LIKE %:name%")
	List<Product> findByName(@Param("name") String name);

}
