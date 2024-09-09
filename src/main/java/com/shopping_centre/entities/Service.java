package com.shopping_centre.entities;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "SERVICE")
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long s_id;
	private String s_name;
	private String mainImage;
	private String price;
	private String category;
	
	@Lob
	@Column(length = 60000)
	private List<String> subImages = new ArrayList<>();
	@Lob
	@Column(length = 60000)
	private List<String> features = new ArrayList<>();
	// private Blob blob = (Blob) features;
	/*
	 * @ManyToOne
	 * 
	 * @JsonIgnore private Seller seller;
	 */
	@ManyToMany
	private List<Customer> customers = new ArrayList<>();

	/*
	 * @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy =
	 * "service") private List<CustomerItem> customerItem = new ArrayList<>();
	 */
	public long getS_id() {
		return s_id;
	}

	public void setS_id(long s_id) {
		this.s_id = s_id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public List<String> getSubImages() {
		return subImages;
	}

	public void setSubImages(List<String> subImages) {
		this.subImages = subImages;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	/*
	 * public Seller getSeller() { return seller; }
	 * 
	 * public void setSeller(Seller seller) { this.seller = seller; }
	 */

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/*
	 * public List<CustomerItem> getCustomerItem() { return customerItem; }
	 * 
	 * public void setCustomerItem(List<CustomerItem> customerItem) {
	 * this.customerItem = customerItem; }
	 */

	
	
	
	
}
