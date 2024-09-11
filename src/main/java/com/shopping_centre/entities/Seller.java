package com.shopping_centre.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "SELLER")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Please enter valid email address")
	private String email;
	@NotBlank(message = "Name field is required")
	@Size(min = 2, max = 20, message = "name must be atleast 2 and maximum 20 characters long")
	private String name;
	@NotBlank(message = "Bussiness Name cannot be blank")
	private String bussinessName;
	@NotBlank(message = "Please select bussiness type")
	private String bussinessType;
	@NotBlank(message = "State cannot be blank")
	private String state;
	@NotBlank(message = "Street1 cannot be blank")
	private String street1;
	// private String street2;
	@NotBlank(message = "Password field is required")
	@Column(columnDefinition = "varchar(255) default 'abc'")
	private String password;
	@Transient
	private String confirmPassword;
	@Size(min = 10, max = 10, message = "Please enter valid mobile number")
	private String phone;

	// private String address;

	@NotBlank(message = "City name cannot be blank")
	@Column(columnDefinition = "varchar(255) default 'abc'")
	private String city;
	/*
	 * @Size(min=6,max=6,message="Please enter valid PIN Code") private String
	 * pinCode;
	 */
	// private String finalAddress;
	private String image;
	private String addCheck;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "seller")
	// @OneToMany
	private List<Product> products = new ArrayList<>();
	/*
	 * @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy =
	 * "seller") // @OneToMany private List<Service> services = new ArrayList<>();
	 */
	private String registrationStatus;
	private String accountId;
	private boolean emailVerification;
	private boolean mobileVerification;
	private boolean termsAndConditionsAgreed;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bank_accont_id", referencedColumnName = "id")
	private BankAccount bankAccount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * public String getPinCode() { return pinCode; } public void setPinCode(String
	 * pinCode) { this.pinCode = pinCode; }
	 */
	public String getImage() {
		return image;
	}

	public String getAddCheck() {
		return addCheck;
	}

	public void setAddCheck(String addCheck) {
		this.addCheck = addCheck;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	/*
	 * public String getStreet2() { return street2; } public void setStreet2(String
	 * street2) { this.street2 = street2; }
	 */

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/*
	 * public List<Service> getServices() { return services; } public void
	 * setServices(List<Service> services) { this.services = services; }
	 */
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public boolean isEmailVerification() {
		return emailVerification;
	}

	public void setEmailVerification(boolean emailVerification) {
		this.emailVerification = emailVerification;
	}

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

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/*
	 * public String getFinalAddress() { return finalAddress; } public void
	 * setFinalAddress(String finalAddress) { this.finalAddress = finalAddress; }
	 */

}
