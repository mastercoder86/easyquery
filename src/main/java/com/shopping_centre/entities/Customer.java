package com.shopping_centre.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	/*
	 * @Column(unique = true)
	 * 
	 * @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message =
	 * "Please enter valid email address") private String email;
	 */
	@NotBlank(message = "Name field is required")
	@Size(min = 2, max = 20, message = "name must be atleast 2 and maximum 20 characters long")
	private String name;
	/*
	 * @NotBlank(message = "Password field is required") //@Column(columnDefinition
	 * = "varchar(255) default 'abc'") private String password;
	 * 
	 * @Transient private String confirmPassword;
	 */
	@Column(unique = true)
	@Size(min = 10, max = 10, message = "Please enter valid mobile number")
	private String phone;
	/*
	 * @NotBlank private String address;
	 */
	// private String finalAddress;

	// @NotBlank(message = "Password field is required")

	// @Column(columnDefinition = "varchar(255) default 'abc'")

	/* private String city="abc"; */
	// @Size(min = 6, max = 6, message = "Please enter valid PIN Code")
	// private String pinCode;
	private boolean logIn;
	/*
	 * private String image; private String addCheck;
	 */

	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "customers")
	//@JoinTable(name = "customer_product", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "p_id"))
	private List<Product> products = new ArrayList<>();

	// private boolean emailVerification;
	private boolean mobileVerification;
	private boolean termsAndConditionsAgreed;
	private String startSearch = "value3";

	/*
	 * @ManyToMany
	 * 
	 * @JoinTable(name = "customer_service", joinColumns = @JoinColumn(name =
	 * "customer_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
	 * private List<Service> services = new ArrayList<>();
	 */
	/*
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
	 * true)
	 * 
	 * private List<CustomerItem> customerItem = new ArrayList<>();
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	 * public String getPinCode() { return pinCode; }
	 * 
	 * 
	 * public String getFinalAddress() { return finalAddress; }
	 * 
	 * public void setFinalAddress(String finalAddress) { this.finalAddress =
	 * finalAddress; }
	 * 
	 * public void setPinCode(String pinCode) { this.pinCode = pinCode; }
	 */

	public boolean isLogIn() {
		return logIn;
	}

	public void setLogIn(boolean logIn) {
		this.logIn = logIn;
	}

	/*
	 * public List<Product> getProducts() { return products; }
	 * 
	 * public void setProducts(List<Product> products) { this.products = products; }
	 * 
	 * public List<Service> getServices() { return services; }
	 * 
	 * public void setServices(List<Service> services) { this.services = services; }
	 */

	/*
	 * public List<CustomerItem> getCustomerItem() { return customerItem; }
	 * 
	 * public void setCustomerItem(List<CustomerItem> customerItem) {
	 * this.customerItem = customerItem; }
	 */

	/*
	 * public List<Product> getProducts() { return products; }
	 * 
	 * public void setProducts(List<Product> products) { this.products = products; }
	 */

	public boolean isMobileVerification() {
		return mobileVerification;
	}

	public void setMobileVerification(boolean mobileVerification) {
		this.mobileVerification = mobileVerification;
	}

	public boolean isTermsAndConditionsAgreed() {
		return termsAndConditionsAgreed;
	}

	public void setTermsAndConditionsAgreed(boolean termsAndConditionsAgreed) {
		this.termsAndConditionsAgreed = termsAndConditionsAgreed;
	}

	public String getStartSearch() {
		return startSearch;
	}

	public void setStartSearch(String startSearch) {
		this.startSearch = startSearch;
	}

}
