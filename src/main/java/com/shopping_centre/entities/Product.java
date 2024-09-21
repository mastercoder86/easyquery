package com.shopping_centre.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long p_id;
	private String p_name;
	private String mainImage;
	private String price;
	private String category;

	@Transient
	private String unit;
	@Transient
	private Double qty;
	@Transient
	private Double tempPrice;
	@Transient
	private Integer qty2;
	@Transient
	private Integer tempPrice1;
	private String exchangePolicy;
	private String refundPolicy;

	// @Column(length = 60000)

	@Lob
	private List<String> subImages = new ArrayList<>();

	// @Column(length = 60000)
	@Lob
	private List<String> features = new ArrayList<>();

	@ManyToOne
	@JsonIgnore
	private Seller seller;

	@ManyToMany
	private List<Customer> customers = new ArrayList<>();

	/*
	 * @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name="product_id",foreignKey = @ForeignKey(name="none")) private
	 * List<CustomerItem> customerItem = new ArrayList<>();
	 */

	public String getMainImage() {
		return mainImage;
	}

	public long getP_id() {
		return p_id;
	}

	public void setP_id(long p_id) {
		this.p_id = p_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getRefundPolicy() {
		return refundPolicy;
	}

	public void setRefundPolicy(String refundPolicy) {
		this.refundPolicy = refundPolicy;
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

	public String getExchangePolicy() {
		return exchangePolicy;
	}

	public void setExchangePolicy(String exchangePolicy) {
		this.exchangePolicy = exchangePolicy;
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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	/*
	 * public List<Customer> getCustomers() { return customers; }
	 * 
	 * public void setCustomers(List<Customer> customers) { this.customers =
	 * customers; }
	 */

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getTempPrice() {
		return tempPrice;
	}

	public void setTempPrice(Double tempPrice) {
		this.tempPrice = tempPrice;
	}

	public Integer getQty2() {
		return qty2;
	}

	public void setQty2(Integer qty2) {
		this.qty2 = qty2;
	}

	public Integer getTempPrice1() {
		return tempPrice1;
	}

	public void setTempPrice1(Integer tempPrice1) {
		this.tempPrice1 = tempPrice1;
	}

	/*
	 * public List<CustomerItem> getCustomerItem() { return customerItem; }
	 * 
	 * public void setCustomerItem(List<CustomerItem> customerItem) {
	 * this.customerItem = customerItem; }
	 */

}
