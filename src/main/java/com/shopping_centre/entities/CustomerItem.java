package com.shopping_centre.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
@Entity
@Table(name = "CUSTOMER_ITEMS")
public class CustomerItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String status;
	private Long customerId;
	private Long productId;
	private Long serviceId;
	private Double qty;
	private Double price;
	private Integer qty2;
	private Integer price1;
	/*
	 * @ManyToOne
	 * 
	 * @JsonIgnore
	 * 
	 * @JoinColumn(name="customer_id",foreignKey = @ForeignKey(name="none")) private
	 * Customer customer;
	 * 
	 * @ManyToOne
	 * 
	 * @JsonIgnore private Service service;
	 * 
	 * @ManyToOne
	 * 
	 * @JsonIgnore //@JoinColumn(unique = false) private Product product;
	 */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getQty2() {
		return qty2;
	}
	public void setQty2(Integer qty2) {
		this.qty2 = qty2;
	}
	public Integer getPrice1() {
		return price1;
	}
	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}
	
	
	
	
}
