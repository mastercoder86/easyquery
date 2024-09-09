package com.shopping_centre.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Transfer;
import com.shopping_centre.dao.CustomerItemRepository;
import com.shopping_centre.dao.CustomerRepository;
import com.shopping_centre.dao.MyOrderRepository;
import com.shopping_centre.dao.ProductRepository;
import com.shopping_centre.dao.SellerRepository;
import com.shopping_centre.entities.BankAccount;
import com.shopping_centre.entities.Customer;
import com.shopping_centre.entities.CustomerItem;
import com.shopping_centre.entities.MyOrder;
import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;

import jakarta.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private CustomerItemRepository customerItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MyOrderRepository myOrderRepository;

	@GetMapping("/")
	public String home() {
		return "customer_index";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("customer") Customer customer, BindingResult result1,
			@RequestParam String cAddressStatus,@RequestParam(required=false) String agreement, Model model) {
		// model.addAttribute("clicked","yes");
//		model.addAttribute("namerror1",null);
		model.addAttribute("test1", "test1");

		model.addAttribute("bankAccount", new BankAccount());
		boolean flag = true;
		// boolean flag2 = false;
		if (result1.hasErrors()) {
			System.out.println("inside process register");
			model.addAttribute("customer", customer);
			model.addAttribute("seller", new Seller());
			model.addAttribute("credentials3", "bad");
			List<FieldError> errors = result1.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
				if (error.getDefaultMessage().contains("Name")) {
					model.addAttribute("namerror1", "namerror1");
					model.addAttribute("namerrormessage1", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("email")) {
					model.addAttribute("emailerror1", "emailerror1");
					model.addAttribute("emailerrormessage1", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("Password")) {
					model.addAttribute("passworderror1", "passworderror1");
					model.addAttribute("passworderrormessage1", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("Please reenter your password")) {
					model.addAttribute("confirmpassworderror1", "confirmpassworderror1");
					model.addAttribute("confirmpassworderrormessage1", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("mobile")) {
					model.addAttribute("phoneerror1", "phoneerror1");
					model.addAttribute("phoneerrormessage1", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("PIN")) {
					model.addAttribute("pincodeerror1", "pincodeerror1");
					model.addAttribute("pincodeerrormessage1", error.getDefaultMessage());
				}
				if (error.getField().equals("address")) {
					model.addAttribute("addressError", "This field is required");
				}
				/*
				 * if (error.getDefaultMessage().contains("address")) {
				 * model.addAttribute("addresserror1", "addresserror1");
				 * model.addAttribute("addresserrormessage1", error.getDefaultMessage()); } if
				 * (error.getDefaultMessage().contains("city")) {
				 * model.addAttribute("cityerror1", "cityerror1");
				 * model.addAttribute("cityerrormessage1", error.getDefaultMessage()); }
				 * 
				 */

			}
			if (cAddressStatus.equals("show")) {
				model.addAttribute("cAddress", "show");
			}
			model.addAttribute("customer", customer);
			return "login_form";
			// return "index";

		} else {
			System.out.println("inside process register 2");
			model.addAttribute("credentials3", "good");

			/*
			 * if (sub != null) { model.addAttribute("credentials3", "emailproblem");
			 * model.addAttribute("seller", seller);
			 * 
			 * return "index"; }
			 */
			if (customer.getPassword().compareTo(customer.getConfirmPassword()) != 0) {
				/*
				 * model.addAttribute("credentials3", "passwordproblem");
				 * model.addAttribute("seller", seller); System.out.println("don't match");
				 */
				/* return "index"; */
				model.addAttribute("confirmpassworderror1", "confirmpassworderror1");
				model.addAttribute("confirmpassworderrormessage1",
						"Password entered don't match with above entered password");
				model.addAttribute("credentials3", "bad");
				model.addAttribute("customer", customer);
				model.addAttribute("seller", new Seller());
				flag = false;
				return "login_form";
				// return "index";

			}
			try {
				Double.parseDouble(customer.getPhone());
			} catch (NumberFormatException e) {
				System.out.println("please enter valid mobile number");
				model.addAttribute("phoneerror1", "phoneerror1");
				model.addAttribute("phoneerrormessage1", "please enter valid mobile number");
				model.addAttribute("credentials3", "bad");
				model.addAttribute("customer", customer);
				model.addAttribute("seller", new Seller());
				flag = false;
				return "login_form";
				// return "index";

			}
			if (agreement == null) {
				/*
				 * model.addAttribute("credentials", "passwordproblem");
				 * model.addAttribute("seller", seller); System.out.println("don't match");
				 */
				/* return "index"; */
				/*
				 * model.addAttribute("confirmpassworderror", "confirmpassworderror");
				 * model.addAttribute("confirmpassworderrormessage",
				 * "Password entered don't match with above entered password");
				 */
				model.addAttribute("credentials3", "cu no agreement");
				model.addAttribute("customer", customer);
				model.addAttribute("seller", new Seller());
				flag = false;
				return "login_form";
				// return "index";

			}
			/*
			 * try { Double.parseDouble(customer.getPinCode()); } catch
			 * (NumberFormatException e) {
			 * System.out.println("please enter valid PIN Code");
			 * model.addAttribute("pincodeerror1", "pincodeerror1");
			 * model.addAttribute("pincodeerrormessage1", "please enter valid PIN Code");
			 * model.addAttribute("credentials3", "bad"); model.addAttribute("seller",
			 * customer); model.addAttribute("seller", new Seller()); flag = false; return
			 * "index"; return "login_form"; }
			 */
			/* model.addAttribute("seller", new Seller()); */
			// seller.setAddCheck("add1");
			if (flag) {
				customer.setLogIn(true);
				model.addAttribute("seller",new Seller());
				try {
					customer.setTermsAndConditionsAgreed(true);
					customerRepository.save(customer);
					model.addAttribute("credentials3", "verification pending");
				} catch (DataIntegrityViolationException e) {
					model.addAttribute("cvError", "This email is already registered");
					model.addAttribute("customer", customer);
					model.addAttribute("credentials3", "bad");
					return "login_form";
				}
				/*
				 * String address = customer.getAddress(); String api_key =
				 * "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk"; String address1 = null;
				 * 
				 * if (address.contains("  ")) { address1 = address.replace("  ", " ");
				 * 
				 * } String address2 = null; if (address1 != null) address2 =
				 * address1.replace(" ", "%20").replace(",", "%2C%20"); else address2 =
				 * address.replace(" ", "%20").replace(",", "%2C%20"); URL url = null; try { url
				 * = new URL(
				 * "https://maps.googleapis.com/maps/api/place/queryautocomplete/json?input=" +
				 * address2 + "&key=" + api_key); } catch (MalformedURLException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } HttpURLConnection httpConn
				 * = null; try { httpConn = (HttpURLConnection) url.openConnection(); } catch
				 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
				 * try { httpConn.setRequestMethod("GET"); } catch (ProtocolException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 * 
				 * InputStream responseStream = null; try { responseStream =
				 * httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() :
				 * httpConn.getErrorStream(); } catch (IOException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } Scanner s = new
				 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
				 * s.next() : "";
				 * 
				 * try { JSONObject myResponse = new JSONObject(response); JSONArray predictions
				 * = (JSONArray) myResponse.get("predictions"); List<String> desciptions = new
				 * ArrayList<>(); for (int i = 0; i < predictions.length(); i++) {
				 * desciptions.add(predictions.getJSONObject(i).getString("description")); }
				 * String choosenAddress = ""; for (String description : desciptions) { if
				 * (description.length() > choosenAddress.length()) choosenAddress =
				 * description;
				 * 
				 * } customer.setFinalAddress(choosenAddress);
				 * customerRepository.save(customer); System.out.println("Addresss : " +
				 * choosenAddress); // System.out.println(description); //
				 * System.out.println(response); // return ResponseEntity.ok(desciptions);
				 * 
				 * } catch (JSONException e) { e.printStackTrace(); // return
				 * ResponseEntity.ok(null); }
				 */

			}

		}
		String greetName;
		try {
			greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
		} catch (StringIndexOutOfBoundsException se) {
			greetName = customer.getName();
		}

		model.addAttribute("c_email", customer.getEmail());
		model.addAttribute("greeting", greetName);
		model.addAttribute("customer", new Customer());
		model.addAttribute("seller", new Seller());
		// model.addAttribute("add_check", seller.getAddCheck());
		return "login_form";
		/* return "add_item"; */
	}

	@PostMapping("/do_register1")
	public String doRegister1(@RequestParam String c_email, @RequestParam String greeting, Model model) {
		model.addAttribute("c_email", c_email);
		model.addAttribute("greeting", greeting);
		model.addAttribute("bankAccount", new BankAccount());

		Customer customer = customerRepository.findByEmail(c_email);
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		List<Product> products = new ArrayList<>();

		double totalPrice = 0;
		// Double totalPrice1 = 0;
		for (CustomerItem ci : customerItems) {
			Product p = productRepository.findById(ci.getProductId()).get(); //
			System.out.println("p_price : " + p.getPrice());
			// String price = p.getPrice();

			// else {
			if (p.getPrice().contains("/")) {
				String unit = p.getPrice().substring(p.getPrice().indexOf("/") + 1);
				System.out.println("unit :" + unit);
				if (ci.getQty() != null) {

					p.setQty(ci.getQty());
					p.setTempPrice(ci.getPrice());
					System.out.println("hello :" + p.getPrice());
					totalPrice += p.getTempPrice();
				} else {
					p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
					totalPrice += p.getTempPrice();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}

				p.setUnit(unit);
			} else {
				if (ci.getQty2() != null) {

					p.setQty2(ci.getQty2());
					p.setTempPrice1(ci.getPrice1());
					System.out.println("hello :" + p.getPrice());
					totalPrice += p.getTempPrice1();
				} else {
					p.setTempPrice1(Integer.parseInt(p.getPrice()));
					totalPrice += p.getTempPrice1();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}
				p.setUnit("");
			}
			// }
			// String price = p.getPrice();
			// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
			/* totalPrice+=p.getTempPrice(); */

			/*
			 * if (p.getTempPrice() != null) { totalPrice += p.getTempPrice();
			 * p.setTempPrice(null); } else { totalPrice +=
			 * Double.parseDouble(p.getPrice()); }
			 * 
			 * if (p.getTempPrice1() != null) { totalPrice += p.getTempPrice1();
			 * p.setTempPrice1(null); } else { totalPrice +=
			 * Double.parseDouble(p.getPrice()); }
			 */

			/*
			 * if (p.getTempPrice1() != null) { totalPrice += p.getTempPrice1(); } else {
			 * totalPrice += Double.parseDouble(p.getPrice()); }
			 */
			products.add(p);
			// p.setPrice(price);
			System.out.println("list :" + products);
		}
		model.addAttribute("products", products);
		model.addAttribute("totalPrice", totalPrice);
		String greetName;
		try {
			greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
		} catch (StringIndexOutOfBoundsException se) {
			greetName = customer.getName();
		}
		model.addAttribute("greeting", greetName);
		// model.addAttribute("allowed", "yes");
		model.addAttribute("product_added", "yes");
		// model.addAttribute("product", product1);
		if (products != null) {
			model.addAttribute("noOfItems", products.size());
		}

		if (products.isEmpty()) {
			model.addAttribute("products", null);
		}
		model.addAttribute("c_email", c_email);
		// System.out.println(product_id + ":" + p_id);
		// model.addAttribute("removed", "removed");
		/*
		 * if (product_id == p_id) { System.out.println("matched");
		 * model.addAttribute("removed", "removed"); }
		 */
		return "customer_index";
	}

	@PostMapping("/do_login")
	public String login(@ModelAttribute Customer customer, Model model) {
		System.out.println("inside login");
		Customer customer1 = customerRepository.findByEmailAndPassword(customer.getEmail(), customer.getPassword());
		model.addAttribute("seller", new Seller());
		model.addAttribute("bankAccount", new BankAccount());
		if (customer1 != null) {
			if(!customer1.isEmailVerification()) {
				model.addAttribute("mo_verification", customer1.isMobileVerification());
				model.addAttribute("credentials", "ma_verification_pending");
				model.addAttribute("c_email",customer1.getEmail());
				return "login_form";
			} else if (!customer1.isMobileVerification()) {
				model.addAttribute("ma_verification", customer1.isEmailVerification());
				model.addAttribute("credentials","mo_verification_pending");
				model.addAttribute("c_email",customer1.getEmail());
				return "login_form";
			}
			String greetName;
			try {
				greetName = customer1.getName().substring(0, customer1.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = customer1.getName();
			}
			model.addAttribute("c_email", customer1.getEmail());
			model.addAttribute("greeting", greetName);

			// model.addAttribute("add_check", seller1.getAddCheck());
			// model.addAttribute("backB", "true");
			// model.addAttribute("seller", new Seller());
			/*
			 * model.addAttribute("p_list",
			 * productRepository.findProductsBySeller(sellerRepository.findByEmail(seller1.
			 * getEmail()))); model.addAttribute("s_list",
			 * serviceRepository.findServicesBySeller(sellerRepository.findByEmail(seller1.
			 * getEmail()))); if (seller1.getProducts().isEmpty()) {
			 * model.addAttribute("p_list", null); } else { if
			 * (((seller1.getProducts().size()) % 4) == 0) {
			 * model.addAttribute("length_check", "change"); } } if
			 * (seller1.getServices().isEmpty()) { model.addAttribute("s_list", null); }
			 * else { if (((seller1.getServices().size()) % 3) == 0) {
			 * model.addAttribute("length_check1", "change"); } }
			 */
			customer1.setLogIn(true);
			customerRepository.save(customer1);
			/* Customer customer = customerRepository.findByEmail(c_email); */
			List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer1.getId(),
					"added_to_cart");
			List<Product> products = new ArrayList<>();

			double totalPrice = 0;
			// Double totalPrice1 = 0;
			for (CustomerItem ci : customerItems) {
				Product p = productRepository.findById(ci.getProductId()).get(); //
				System.out.println("p_price : " + p.getPrice());
				// String price = p.getPrice();

				// else {
				if (p.getPrice().contains("/")) {
					String unit = p.getPrice().substring(p.getPrice().indexOf("/") + 1);
					System.out.println("unit :" + unit);
					if (ci.getQty() != null) {

						p.setQty(ci.getQty());
						p.setTempPrice(ci.getPrice());
						System.out.println("hello :" + p.getPrice());
						totalPrice += p.getTempPrice();
					} else {
						p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
						totalPrice += p.getTempPrice();
						// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
					}

					p.setUnit(unit);
				} else {
					if (ci.getQty2() != null) {

						p.setQty2(ci.getQty2());
						p.setTempPrice1(ci.getPrice1());
						System.out.println("hello :" + p.getPrice());
						totalPrice += p.getTempPrice1();
					} else {
						p.setTempPrice1(Integer.parseInt(p.getPrice()));
						totalPrice += p.getTempPrice1();
						// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
					}
					p.setUnit("");
				}
				// }
				// String price = p.getPrice();
				// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
				/* totalPrice+=p.getTempPrice(); */

				/*
				 * if (p.getTempPrice() != null) { totalPrice += p.getTempPrice();
				 * p.setTempPrice(null); } else { totalPrice +=
				 * Double.parseDouble(p.getPrice()); }
				 * 
				 * if (p.getTempPrice1() != null) { totalPrice += p.getTempPrice1();
				 * p.setTempPrice1(null); } else { totalPrice +=
				 * Double.parseDouble(p.getPrice()); }
				 */

				/*
				 * if (p.getTempPrice1() != null) { totalPrice += p.getTempPrice1(); } else {
				 * totalPrice += Double.parseDouble(p.getPrice()); }
				 */
				products.add(p);
				// p.setPrice(price);
				System.out.println("list :" + products);
			}
			model.addAttribute("products", products);
			model.addAttribute("totalPrice", totalPrice);
			/*
			 * String greetName; try { greetName = customer.getName().substring(0,
			 * customer.getName().indexOf(" ")); } catch (StringIndexOutOfBoundsException
			 * se) { greetName = customer.getName(); }
			 */
			// model.addAttribute("greeting", greetName);
			// model.addAttribute("allowed", "yes");
			model.addAttribute("product_added", "yes");
			// model.addAttribute("product", product1);
			if (products != null) {
				model.addAttribute("noOfItems", products.size());
			}

			if (products.isEmpty()) {
				model.addAttribute("products", null);
			}
			// model.addAttribute("c_email", c_email);
		} else {
			model.addAttribute("login_credentials4", "bad");
			// model.addAttribute("backB", "false");
			return "login_form";
		}
		// System.out.println("size:" + .getProducts().size());
		return "customer_index";
	}

	@PostMapping("/logged_in")
	public String customerLoggedIn(@RequestParam String email, Model model) {
		Customer customer = customerRepository.findByEmail(email);
		String greetName;
		// if (customer != null) {
		try {
			greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
		} catch (StringIndexOutOfBoundsException se) {
			greetName = customer.getName();
		}
		model.addAttribute("greeting", greetName);
		model.addAttribute("c_email", email);
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		List<Product> products = new ArrayList<>();
		double totalPrice = 0;
		for (CustomerItem ci : customerItems) {
			Product p = productRepository.findById(ci.getProductId()).get();
			String price = p.getPrice();
			// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
			/*
			 * if (p.getPrice().contains("/")) { String unit =
			 * p.getPrice().substring(p.getPrice().indexOf("/") + 1);
			 * p.setTempPrice(Double.parseDouble(p.getPrice().substring(0,
			 * p.getPrice().indexOf("/")))); p.setTePrice(p.getPrice().substring(0,
			 * p.getPrice().indexOf("/"))); totalPrice += p.getTempPrice(); p.setUnit(unit);
			 * } else {
			 * 
			 * p.setTempPrice1(Integer.parseInt(p.getPrice()));
			 * p.setTePrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
			 * totalPrice += p.getTempPrice1(); p.setUnit(""); }
			 */
			if (p.getPrice().contains("/")) {
				String unit = p.getPrice().substring(p.getPrice().indexOf("/") + 1);
				System.out.println("unit :" + unit);
				if (ci.getQty() != null) {

					p.setQty(ci.getQty());
					p.setTempPrice(ci.getPrice());
					System.out.println("hello :" + p.getPrice());
					totalPrice += p.getTempPrice();
				} else {
					p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
					totalPrice += p.getTempPrice();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}

				p.setUnit(unit);
			} else {
				if (ci.getQty2() != null) {

					p.setQty2(ci.getQty2());
					p.setTempPrice1(ci.getPrice1());
					System.out.println("hello :" + p.getPrice());
					totalPrice += p.getTempPrice1();
				} else {
					p.setTempPrice1(Integer.parseInt(p.getPrice()));
					totalPrice += p.getTempPrice1();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}
				p.setUnit("");
			}
			products.add(p);
			// p.setPrice(price);
			System.out.println("list :" + products);
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("products", products);
		/*
		 * String greetName; try { greetName = customer.getName().substring(0,
		 * customer.getName().indexOf(" ")); } catch (StringIndexOutOfBoundsException
		 * se) { greetName = customer.getName(); } model.addAttribute("greeting",
		 * greetName);
		 */
		// model.addAttribute("allowed", "yes");
		model.addAttribute("product_added", "yes");
		// model.addAttribute("product", product);
		if (products != null) {
			model.addAttribute("noOfItems", products.size());
		}

		if (products.isEmpty()) {
			model.addAttribute("products", null);
		}
		// model.addAttribute("c_email", c_email);
		model.addAttribute("manage_cart", "no show");
		return "customer_index";
		// }

		// return "index";

	}

	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) {
		System.out.println("Hey order function ex");
		System.out.println(data);
		Customer customer = customerRepository.findByEmail(data.get("email").toString());
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		// List<String> p_ids = (List<String>) data.get("products");
		/*
		 * for(String p_id : p_ids) System.out.println(p_id);
		 */

		int amount = Integer.parseInt(data.get("amount").toString());
		Order order = null;
		try {
			RazorpayClient client = new RazorpayClient("rzp_test_y84XqHSv5nPYer", "aRNRG1XGthPYkdebPAGg0IWN");
			JSONObject orderRequest = new JSONObject();
			try {
				orderRequest.put("amount", amount * 100);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				orderRequest.put("currency", "INR");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				orderRequest.put("receipt", "txn_57890");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * JSONObject notes = new JSONObject();
			 * notes.put("notes_key_1","Tea, Earl Grey, Hot");
			 */
			/* orderRequest.put("notes",notes); */

			// creating new order
			order = client.orders.create(orderRequest);
			System.out.println(order);
			// System.out.println(order.get("razorpay_payment_id").);
			MyOrder myOrder = new MyOrder();
			myOrder.setAmount("Rs." + Double.parseDouble(order.get("amount").toString()) / 100);
			myOrder.setOrderId(order.get("id"));
			myOrder.setPaymentId(null);
			myOrder.setStatus("created");
			myOrder.setCustomer(customerRepository.findByEmail(data.get("email").toString()));
			myOrder.setReceipt(order.get("receipt"));

			myOrderRepository.save(myOrder);
			/*
			 * for (CustomerItem ci : customerItems) { if (ci.getPrice() == null &&
			 * ci.getPrice1() == null) { Product product =
			 * productRepository.findById(ci.getProductId()).get(); if
			 * (product.getPrice().contains("/")) { int price =
			 * Integer.parseInt(product.getPrice().substring(0,
			 * product.getPrice().indexOf("/"))); System.out.println(price); } else { price
			 * = }
			 * 
			 * } else { if (ci.getPrice() != null) { System.out.println(ci.getPrice()); int
			 * price = (int) Math.round(ci.getPrice()); System.out.println(price); }
			 * 
			 * else System.out.println(ci.getPrice1()); }
			 * 
			 * }
			 */
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error occured");
		}
		return order.toString();
	}

	// update order
	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data) {
		System.out.println(data);
		Customer customer = customerRepository.findByEmail(data.get("email").toString());
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		MyOrder myOrder = myOrderRepository.findByOrderId(data.get("razorpay_order_id").toString());
		myOrder.setPaymentId(data.get("razorpay_payment_id").toString());
		myOrder.setStatus(data.get("status").toString());
		myOrderRepository.save(myOrder);
		RazorpayClient razorpay = null;
		try {
			razorpay = new RazorpayClient("rzp_test_y84XqHSv5nPYer", "aRNRG1XGthPYkdebPAGg0IWN");
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (CustomerItem ci : customerItems) {
			int price = 0;
			Product product = productRepository.findById(ci.getProductId()).get();
			if (ci.getPrice() == null && ci.getPrice1() == null) {

				if (product.getPrice().contains("/")) {
					price = Integer.parseInt(product.getPrice().substring(0, product.getPrice().indexOf("/")));
					System.out.println(price);
				} else {
					price = Integer.parseInt(product.getPrice());
				}
			} else {
				if (ci.getPrice() != null) {
					System.out.println(ci.getPrice());
					price = (int) Math.round(ci.getPrice());
					System.out.println(price);
				}

				else {
					price = ci.getPrice1();
				}
				System.out.println(ci.getPrice1());

			}
			String paymentId = data.get("razorpay_payment_id").toString();
			// String paymentId = "pay_E8JR8E0XyjUSZd";
			JSONObject transferRequest = new JSONObject();

			JSONObject transferParams = new JSONObject();
			try {
				transferParams.put("account", product.getSeller().getAccountId());

				System.out.println("test1 : " + product.getSeller().getAccountId());
				transferParams.put("amount", price * 100);
				transferParams.put("currency", "INR");
				JSONObject notes = new JSONObject();

				notes.put("name", product.getSeller().getName());
				System.out.println("test2 : " + product.getSeller().getName());
				notes.put("roll_no", "easy_query" + product.getSeller().getId());
				// notes.put("roll_no", "IEC2011026");
				transferParams.put("notes", notes);
				List<Object> linkedAccountNotes = new ArrayList<>();
				linkedAccountNotes.add("roll_no");

				transferParams.put("linked_account_notes", linkedAccountNotes);
				transferParams.put("on_hold", true);
				List<Object> transfers = new ArrayList<>();
				transfers.add(transferParams);
				transferRequest.put("transfers", transfers);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				List<Transfer> transfer = razorpay.payments.transfer(paymentId, transferRequest);
				for (Transfer t : transfer)
					System.out.println("TRANSFER : " + t);

			} catch (RazorpayException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			// return ResponseEntity.ok(Map.of("msg", "updated"));

		}

		System.out.println(data);
		return ResponseEntity.ok(Map.of("msg", "updated"));
	}

	@GetMapping("/distance")
	public String distance() {
		String address = sellerRepository.findByEmail("budhakumar21@gmail.com").getStreet1();
		// String address = "Narengi,Assam";
		String address1 = null;
		String api_key = "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk";
		if (address.contains("  ")) {
			address1 = address.replace("  ", " ");

		}
		String address2 = null;
		if (address1 != null)
			address2 = address1.replace(" ", "%20").replace(",", "%2C%20");
		else
			address2 = address.replace(" ", "%20").replace(",", "%2C%20");
		address = customerRepository.findByEmail("budhakumar22@gmail.com").getAddress();
		if (address.contains("  ")) {
			address1 = address.replace("  ", " ");

		}
		String address3 = null;
		if (address1 != null)
			address3 = address1.replace(" ", "%20").replace(",", "%2C%20");
		else
			address3 = address.replace(" ", "%20").replace(",", "%2C%20");
		// String api_key = "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk";
		System.out.println("address2: " + address2);
		System.out.println("address3: " + address3);
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?destinations=" + address2
					+ "&origins=" + address3 + "&key=" + api_key);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpConn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream responseStream = null;
		try {
			responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
					: httpConn.getErrorStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner s = new Scanner(responseStream).useDelimiter("\\A");
		String response = s.hasNext() ? s.next() : "";
		System.out.println(response);
		/*
		 * try { JSONObject myResponse = new JSONObject(response); JSONArray predictions
		 * = (JSONArray) myResponse.get("predictions"); List<String> desciptions = new
		 * ArrayList<>(); for (int i = 0; i < predictions.length(); i++) {
		 * desciptions.add(predictions.getJSONObject(i).getString("description")); }
		 * String choosenAddress = ""; for (String description : desciptions) { if
		 * (description.length() > choosenAddress.length()) choosenAddress =
		 * description;
		 * 
		 * } System.out.println("Address : " + choosenAddress); } catch (JSONException
		 * e) { e.printStackTrace(); }
		 */
		return "index";
	}

}
