package com.shopping_centre.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.shopping_centre.entities.CustomerItem;


public interface CustomerItemRepository extends JpaRepository<CustomerItem, Long> {
	CustomerItem findByCustomerIdAndProductId(long cid,long pid);
	List<CustomerItem> findByCustomerIdAndStatus(long id,String status);
	CustomerItem findByStatus(String status);
	List<CustomerItem> findByProductId(long id);
}
