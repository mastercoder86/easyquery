package com.shopping_centre.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.RegEx;

import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.apache.commons.lang3.builder.Builder;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.razorpay.Account;
import com.razorpay.FundAccount;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.VirtualAccount;
import com.shopping_centre.dao.BankAccountRepository;
import com.shopping_centre.dao.OtpDetailsRepository;
import com.shopping_centre.dao.ProductRepository;
import com.shopping_centre.dao.SellerRepository;

import com.shopping_centre.entities.BankAccount;
import com.shopping_centre.entities.Customer;
import com.shopping_centre.entities.OtpDetails;
import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;

import com.shopping_centre.service.EmailService;
//import com.shopping_centre.helper.SendSms;
//import com.shopping_centre.helper.SinchSms;
import com.sinch.sdk.domains.sms.BatchesService;
import com.sinch.sdk.domains.sms.DeliveryReportsService;
import com.sinch.sdk.domains.sms.GroupsService;
import com.sinch.sdk.domains.sms.InboundsService;
import com.sinch.sdk.domains.sms.SMSService;
import com.sinch.sdk.domains.sms.WebHooksService;
import com.sinch.xms.ApiConnection;
import com.sinch.xms.ApiException;
import com.sinch.xms.SinchSMSApi;
import com.sinch.xms.api.MtBatchTextSmsResult;

import jakarta.validation.Valid;
import jakarta.xml.bind.DatatypeConverter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductRepository productRepository;

	// @Autowired
	/*
	 * private EmailService emailService;
	 * 
	 * @Autowired private OtpDetailsRepository otpDetailsRepository;
	 * 
	 * @Autowired private ServiceRepository serviceRepository;
	 * 
	 * @Autowired private BankAccountRepository bankAccountRepository;
	 */
	@Autowired
	private BankAccountRepository bankAccountRepository;

	@ModelAttribute
	public void addCommonData(Model model) {

		model.addAttribute("product", new Product());
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("seller") Seller seller, BindingResult result1,
			@RequestParam(required = false) String agreement, Model model) {
		System.out.println(agreement);
		// model.addAttribute("clicked","yes");
//		model.addAttribute("namerror",null);
		model.addAttribute("bankAccount", new BankAccount());

		boolean flag = true;
		// boolean flag2 = false;
		if (result1.hasErrors()) {
			System.out.println("inside process register");
			model.addAttribute("seller", seller);
			model.addAttribute("customer", new Customer());
			model.addAttribute("credentials", "bad");
			List<FieldError> errors = result1.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
				if (error.getDefaultMessage().contains("Name field")) {
					model.addAttribute("namerror", "namerror");
					/* model.addAttribute("namerrormessage", error.getDefaultMessage()); */
					model.addAttribute("namerrormessage", "This field is required");
				}

				if (error.getDefaultMessage().contains("email")) {
					model.addAttribute("emailerror", "emailerror");
					model.addAttribute("emailerrormessage", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("Bussiness Name")) {
					model.addAttribute("bussinessNamerror", "bussinessNamerror");
					/* model.addAttribute("namerrormessage", error.getDefaultMessage()); */
					model.addAttribute("bussinessNamerrormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("bussiness type")) {
					model.addAttribute("bussinessTypeerror", "bussinessTypeerror");
					/* model.addAttribute("namerrormessage", error.getDefaultMessage()); */
					model.addAttribute("bussinessTypeerrormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("Password")) {
					model.addAttribute("passworderror", "passworderror");
					model.addAttribute("passworderrormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("Please reenter your password")) {
					model.addAttribute("confirmpassworderror", "confirmpassworderror");
					model.addAttribute("confirmpassworderrormessage", error.getDefaultMessage());
				}
				if (error.getDefaultMessage().contains("mobile")) {
					model.addAttribute("phoneerror", "phoneerror");
					model.addAttribute("phoneerrormessage", error.getDefaultMessage());
				}

				if (error.getDefaultMessage().contains("Street1")) {
					model.addAttribute("street1error", "street1error");
					model.addAttribute("street1errormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("City")) {
					model.addAttribute("cityerror", "cityerror");
					model.addAttribute("cityerrormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("State")) {
					model.addAttribute("sateerror", "sateerror");
					model.addAttribute("sateerrormessage", "This field is required");
				}
				if (error.getDefaultMessage().contains("PIN")) {
					model.addAttribute("pincodeerror", "pincodeerror");
					model.addAttribute("pincodeerrormessage", error.getDefaultMessage());
				}
				/*
				 * if (error.getDefaultMessage().contains("address")) {
				 * model.addAttribute("addresserror", "addresserror");
				 * model.addAttribute("addresserrormessage", error.getDefaultMessage()); } if
				 * (error.getDefaultMessage().contains("city")) {
				 * model.addAttribute("cityerror", "cityerror");
				 * model.addAttribute("cityerrormessage", error.getDefaultMessage()); }
				 * 
				 */

			}
			return "login_form";
			// return "index";

		} else {
			System.out.println("inside process register 2");
			model.addAttribute("credentials", "good");

			/*
			 * if (sub != null) { model.addAttribute("credentials", "emailproblem");
			 * model.addAttribute("seller", seller);
			 * 
			 * return "index"; }
			 */
			if (seller.getPassword().compareTo(seller.getConfirmPassword()) != 0) {
				/*
				 * model.addAttribute("credentials", "passwordproblem");
				 * model.addAttribute("seller", seller); System.out.println("don't match");
				 */
				/* return "index"; */
				model.addAttribute("confirmpassworderror", "confirmpassworderror");
				model.addAttribute("confirmpassworderrormessage",
						"Password entered don't match with above entered password");
				model.addAttribute("credentials", "bad");
				model.addAttribute("seller", seller);
				model.addAttribute("customer", new Customer());
				flag = false;
				return "login_form";
				// return "index";

			}
			try {
				Long.parseLong(seller.getPhone());
			} catch (NumberFormatException e) {
				System.out.println("please enter valid mobile number");
				model.addAttribute("phoneerror", "phoneerror");
				model.addAttribute("phoneerrormessage", "please enter valid mobile number");
				model.addAttribute("credentials", "bad");
				model.addAttribute("seller", seller);
				model.addAttribute("customer", new Customer());
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
				model.addAttribute("credentials", "no agreement");
				model.addAttribute("seller", seller);
				model.addAttribute("customer", new Customer());
				flag = false;
				return "login_form";
				// return "index";

			}
			/*
			 * try { Integer.parseInt(seller.getPinCode()); } catch (NumberFormatException
			 * e) { System.out.println("please enter valid PIN Code");
			 * model.addAttribute("pincodeerror", "pincodeerror");
			 * model.addAttribute("pincodeerrormessage", "please enter valid PIN Code");
			 * model.addAttribute("credentials", "bad"); model.addAttribute("seller",
			 * seller); model.addAttribute("customer", new Customer()); flag = false; return
			 * "index"; return "login_form"; }
			 */

			/* model.addAttribute("seller", new Seller()); */
			seller.setAddCheck("add1");
			seller.setRegistrationStatus("incomplete");

			/*
			 * String address = seller.getStreet1() + "," + seller.getStreet2() + "," +
			 * seller.getCity() + "," + seller.getState();
			 */

			/*
			 * String address = seller.getStreet1(); String address1 = null; String api_key
			 * = "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk"; if (address.contains("  ")) {
			 * address1 = address.replace("  ", " ");
			 * 
			 * } String address2 = null; if (address1 != null) address2 =
			 * address1.replace(" ", "%20").replace(",", "%2C%20"); else address2 =
			 * address.replace(" ", "%20").replace(",", "%2C%20");
			 * System.out.println(address2); URL url = null; try { url = new URL(
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
			 * s.next() : ""; try { JSONObject myResponse = new JSONObject(response);
			 * JSONArray predictions = (JSONArray) myResponse.get("predictions");
			 * List<String> desciptions = new ArrayList<>(); for (int i = 0; i <
			 * predictions.length(); i++) {
			 * desciptions.add(predictions.getJSONObject(i).getString("description")); }
			 * String choosenAddress = ""; for (String description : desciptions) { if
			 * (description.length() > choosenAddress.length()) choosenAddress =
			 * description;
			 * 
			 * } //seller.setFinalAddress(choosenAddress); System.out.println("Address : " +
			 * choosenAddress); } catch (JSONException e) { e.printStackTrace(); }
			 */

			if (flag) {
				model.addAttribute("customer", new Customer());
				try {
					seller.setTermsAndConditionsAgreed(true);
					sellerRepository.save(seller);
					model.addAttribute("credentials", "verification pending");
				} catch (DataIntegrityViolationException e) {
					model.addAttribute("cvError", "This email is already registered");
					model.addAttribute("seller", seller);
					model.addAttribute("credentials", "bad");
					return "login_form";
				}

				/*
				 * try { URL url = new URL("https://api.razorpay.com/v2/accounts");
				 * HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
				 * httpConn.setRequestMethod("POST");
				 * 
				 * httpConn.setRequestProperty("Content-type", "application/json");
				 * 
				 * byte[] message =
				 * ("rzp_test_y84XqHSv5nPYer:aRNRG1XGthPYkdebPAGg0IWN").getBytes("UTF-8");
				 * String basicAuth = DatatypeConverter.printBase64Binary(message);
				 * httpConn.setRequestProperty("Authorization", "Basic " + basicAuth);
				 * 
				 * httpConn.setDoOutput(true); System.out.println("check :" +
				 * seller.getEmail()); String mail = seller.getEmail(); String phone =
				 * seller.getPhone(); String bussinessType; if
				 * (seller.getBussinessType().contains(" ")) { bussinessType =
				 * seller.getBussinessType().replace(" ", "_").toLowerCase(); } else {
				 * bussinessType = seller.getBussinessType().toLowerCase(); } String referenceId
				 * = "Easy_Query" + seller.getId(); OutputStreamWriter writer = new
				 * OutputStreamWriter(httpConn.getOutputStream()); //
				 * writer.write("{\n \"email\":"+seller.getEmail()+",\n //
				 * \"phone\":"+seller.getPhone()+",\n \"type\":\"route\",\n //
				 * \"reference_id\":\"124124\",\n //
				 * \"legal_business_name\":"+seller.getBussinessName()+",\n //
				 * \"business_type\":"+seller.getBussinessType().toLowerCase()+",\n //
				 * \"contact_name\":"+seller.getName()+",\n \"profile\":{\n //
				 * \"category\":\"healthcare\",\n \"subcategory\":\"clinic\",\n //
				 * \"addresses\":{\n \"registered\":{\n \"street1\":"+seller.getStreet1()+",\n
				 * // \"street2\":"+seller.getStreet2()+",\n \"city\":"+seller.getCity()+",\n //
				 * \"state\":"+seller.getState()+",\n \"postal_code\":"+seller.getPinCode()+",\n
				 * // \"country\":\"IN\"\n }\n }\n },\n \"legal_info\":{\n //
				 * \"pan\":\"AAACL1234C\",\n \"gst\":\"18AABCU9603R1ZM\"\n }\n}");
				 * writer.write(String.format("{\n   \"email\":\"%s\",\n   \"phone\":\"%s\"," +
				 * "\n   \"type\":\"route\",\n   \"reference_id\":\"%s\"," +
				 * "\n   \"legal_business_name\":\"%s\",\n   \"business_type\":\"%s\"," +
				 * "\n   \"contact_name\":\"%s\"," +
				 * "\n   \"profile\":{\n      \"category\":\"healthcare\",\n      \"subcategory\":\"clinic\","
				 * + "\n      \"addresses\":{\n         \"registered\":{" +
				 * "\n            \"street1\":\"%s\"," +
				 * "\n            \"street2\":\"%s\",\n            \"city\":\"%s\"," +
				 * "\n            \"state\":\"%s\",\n            \"postal_code\":\"%s\"," +
				 * "\n            \"country\":\"IN\"\n         }\n      }\n   },\n   \"legal_info\":{"
				 * +
				 * "\n      \"pan\":\"AAACL1234C\",\n      \"gst\":\"18AABCU9603R1ZM\"\n   }\n}"
				 * , mail, phone, referenceId, seller.getBussinessName(), bussinessType,
				 * seller.getName(), seller.getStreet1(), seller.getStreet2(), seller.getCity(),
				 * seller.getState(), seller.getPinCode()));
				 * 
				 * 
				 * writer.flush(); writer.close(); httpConn.getOutputStream().close();
				 * 
				 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
				 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
				 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
				 * s.next() : ""; System.out.println(response); if (response.contains("error"))
				 * { sellerRepository.delete(seller); JSONObject jsonObject = new
				 * JSONObject(response); JSONObject jsonObject2 =
				 * jsonObject.getJSONObject("error"); String error =
				 * jsonObject2.getString("description"); System.out.println(error);
				 * model.addAttribute("mobileError", error); model.addAttribute("credentials",
				 * "bad"); model.addAttribute("seller", seller); return "login_form"; } else {
				 * try { sellerRepository.save(seller); } catch (DataIntegrityViolationException
				 * e) { model.addAttribute("cvError", "This email is already registered");
				 * model.addAttribute("seller", seller); model.addAttribute("credentials",
				 * "bad"); return "login_form"; } } ................. } catch (Exception e) {
				 * e.printStackTrace(); }
				 */

			}

		}
		String greetName;
		try {
			greetName = seller.getName().substring(0, seller.getName().indexOf(" "));
		} catch (StringIndexOutOfBoundsException se) {
			greetName = seller.getName();
		}
		model.addAttribute("s_email", seller.getEmail());
		model.addAttribute("greeting", greetName);
		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		model.addAttribute("add_check", seller.getAddCheck());
		// model.addAttribute("credentials", "incomplete");
		return "login_form";
		/* return "add_item"; */
	}

	@RequestMapping(value = "/do_register1", method = RequestMethod.POST)
	public String register1(@Valid @ModelAttribute("bankAccount") BankAccount bankAccount, BindingResult result1,
			@RequestParam String email, Model model) {
		// model.addAttribute("clicked","yes");
//		model.addAttribute("namerror",null);
		boolean flag = true;
		// boolean flag2 = false;
		if (result1.hasErrors()) {
			System.out.println("inside process register");
			model.addAttribute("bankAccount", bankAccount);
			model.addAttribute("seller", new Seller());
			model.addAttribute("customer", new Customer());
			model.addAttribute("credentials", "ba_bad");
			List<FieldError> errors = result1.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getField() + " - " + error.getDefaultMessage());
				if (error.getField().equals("ifscCode")) {
					model.addAttribute("ifscCodeerror", "ifscCodeerror");
					/* model.addAttribute("namerrormessage", error.getDefaultMessage()); */
					model.addAttribute("ifscCodeerrormessage", "IFSC Code is invalid");
				}

				if (error.getField().equals("accountNumber")) {
					model.addAttribute("accountNumbererror", "accountNumbererror");
					model.addAttribute("accountNumbererrormessage", "Invalid Account Number");
				}
				if (error.getField().equals("beneficiaryName")) {
					model.addAttribute("beneficiaryNameerror", "beneficiaryNameerror");
					/* model.addAttribute("namerrormessage", error.getDefaultMessage()); */
					model.addAttribute("beneficiaryNameerrormessage", "This field is required");
				}

			}
			return "login_form";
			// return "index";

		} else {
			System.out.println("inside process register 2");
			model.addAttribute("credentials", "ba_good");

			/*
			 * if (sub != null) { model.addAttribute("credentials", "emailproblem");
			 * model.addAttribute("seller", seller);
			 * 
			 * return "index"; }
			 */
			if (bankAccount.getAccountNumber().compareTo(bankAccount.getConfirmAccountNumber()) != 0) {
				/*
				 * model.addAttribute("credentials", "passwordproblem");
				 * model.addAttribute("seller", seller); System.out.println("don't match");
				 */
				/* return "index"; */
				model.addAttribute("confirmAccountNumbererror", "confirmAccountNumbererror");
				model.addAttribute("confirmAccountNumbererrormessage",
						"Account Number entered don't match with above entered account number");
				model.addAttribute("credentials", "ba_bad");
				model.addAttribute("bankAccount", bankAccount);
				model.addAttribute("seller", new Seller());
				model.addAttribute("customer", new Customer());
				flag = false;
				return "login_form";
				// return "index";

			}

			/* model.addAttribute("seller", new Seller()); */

			if (flag) {
				Seller seller = sellerRepository.findByEmail(email);

				bankAccount.setStatus("pending");
				bankAccount.setSeller(seller);
				seller.setBankAccount(bankAccount);
				bankAccountRepository.save(bankAccount);
				// sellerRepository.save(seller);
			}

		}

		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());

		model.addAttribute("credentials", "pending");
		return "login_form";
		/* return "add_item"; */
	}

	@PostMapping("/do_login")
	public String login(@ModelAttribute Seller seller, Model model) {
		System.out.println("inside login");
		Seller seller1 = sellerRepository.findByEmailAndPassword(seller.getEmail(), seller.getPassword());
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		if (seller1 != null) {
			if (!seller1.isEmailVerification()) {
				/*
				 * System.out.println("ma pending");
				 * System.out.println(seller.isEmailVerification());
				 */
				model.addAttribute("mo_verification", seller1.isMobileVerification());
				model.addAttribute("credentials", "ma_verification_pending");
				model.addAttribute("s_email", seller.getEmail());
				return "login_form";
			} else if (!seller1.isMobileVerification()) {
				model.addAttribute("ma_verification", seller1.isEmailVerification());
				model.addAttribute("credentials", "mo_verification_pending");
				model.addAttribute("s_email", seller.getEmail());
				return "login_form";
			} else if (seller1.getBankAccount() == null) {
				// model.addAttribute("mo_verification", "pending");
				model.addAttribute("credentials", "no_bank_account");
				model.addAttribute("s_email", seller.getEmail());
				return "login_form";
			} else if (seller1.getBankAccount().getStatus().equals("pending")) {
				model.addAttribute("credentials", "bank_account_verification_pending");
				return "login_form";
			}
			String greetName;
			try {
				greetName = seller1.getName().substring(0, seller1.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = seller1.getName();
			}
			model.addAttribute("s_email", seller1.getEmail());
			model.addAttribute("greeting", greetName);
			model.addAttribute("add_check", seller1.getAddCheck());

			// model.addAttribute("backB", "true");
			// model.addAttribute("seller", new Seller());
			model.addAttribute("p_list",
					productRepository.findProductsBySeller(sellerRepository.findByEmail(seller1.getEmail())));
			/*
			 * model.addAttribute("s_list",
			 * serviceRepository.findServicesBySeller(sellerRepository.findByEmail(seller1.
			 * getEmail())));
			 */
			if (seller1.getProducts().isEmpty()) {
				model.addAttribute("p_list", null);
			} else {
				if (((seller1.getProducts().size()) % 4) == 0) {
					model.addAttribute("length_check", "change");
				}
			}
			/*
			 * if (seller1.getServices().isEmpty()) { model.addAttribute("s_list", null); }
			 * else { if (((seller1.getServices().size()) % 3) == 0) {
			 * model.addAttribute("length_check1", "change"); } }
			 */
		} else {
			model.addAttribute("login_credentials", "bad");
			// model.addAttribute("backB", "false");
			return "login_form";
		}
		System.out.println("size:" + seller1.getProducts().size());
		return "add_item";
	}

	@PostMapping("/do_login1")
	public String login1(@RequestParam String email, @RequestParam String greeting, @RequestParam List<Product> p_list,
			Model model) {

		model.addAttribute("s_email", email);
		model.addAttribute("greeting", greeting);

		model.addAttribute("p_list", p_list);

		return "add_item";
	}

	@GetMapping("/add_item")
	public String addItem(@RequestParam String s_email, @RequestParam String greeting, Model model) {
		model.addAttribute("s_email", s_email);
		model.addAttribute("greeting", greeting);

		return "add_item";
	}

	/*
	 * @GetMapping("/add_item") public String addItem(@ModelAttribute Seller seller,
	 * Model model) {
	 * 
	 * return "add_item"; }
	 */

	@PostMapping("/add_product")
	public String processProduct(@ModelAttribute Product product, @RequestParam("seller_email") String s_email,
			@RequestParam String qty_out, @RequestParam String adding, @RequestParam("main_image") MultipartFile file,
			@RequestParam("sub_image") Optional<MultipartFile[]> files, Model model) {
		System.out.println(s_email);
		System.out.println(qty_out);
		System.out.println(adding);

		try {
			Seller seller = sellerRepository.findByEmail(s_email);
			if (!seller.getAddCheck().equals(adding)) {
				if (file.isEmpty()) {
					System.out.println("File is empty");
				} else {

					product.setMainImage(file.getOriginalFilename());
					File saveFile = new ClassPathResource("/static/images").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Image uploaded");

				}
				if (files.isPresent()) {

					for (MultipartFile f : files.get()) {
						product.getSubImages().add(f.getOriginalFilename());
						File saveFile = new ClassPathResource("static/images").getFile();
						Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + f.getOriginalFilename());
						Files.copy(f.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("Sub Image uploaded");
					}
					if (product.getSubImages().isEmpty()) {
						product.setSubImages(null);
					}
				}
				for (String f1 : product.getFeatures()) {
					System.out.println(f1);
				}

				if (seller != null) {
					System.out.println("password" + seller.getPassword());
					/*
					 * String greetName = seller.getName().substring(0,
					 * seller.getName().indexOf(" "));
					 */
					/*
					 * model.addAttribute("greeting", greetName); model.addAttribute("s_email",
					 * s_email); model.addAttribute("seller", seller);
					 */
				} /*
					 * else { System.out.println("seller is null"); }
					 */
				// product.setDescription(description);
				if (!qty_out.equals("piece")) {
					product.setPrice(product.getPrice() + "/" + qty_out);
				}
				product.setSeller(seller);
				seller.getProducts().add(product);
				seller.setPassword(seller.getPassword());
				seller.setAddCheck(adding);

				sellerRepository.save(seller);
				System.out.println("product added successfully");
				System.out.println("Data : " + product);
				model.addAttribute("p_added", "added");

			}
			String greetName;
			try {
				greetName = seller.getName().substring(0, seller.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = seller.getName();
			}
			model.addAttribute("greeting", greetName);
			model.addAttribute("s_email", s_email);
			model.addAttribute("seller", seller);
			model.addAttribute("p_list", productRepository.findProductsBySeller(sellerRepository.findByEmail(s_email)));
			// model.addAttribute("s_list", serviceRepository.findServicesBySeller(seller));
			model.addAttribute("add_check", seller.getAddCheck());
			if (!seller.getProducts().isEmpty()) {
				if (((seller.getProducts().size()) % 4) == 0) {
					model.addAttribute("length_check", "change");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * session.setAttribute("message", new
			 * Message("Something went wrong !! Try Again", "danger"));
			 */
		}

		return "add_item";
	}

	@PostMapping("/remove_product")
	public String deleteProduct(@RequestParam long id, @RequestParam String seller_email, Model model) {
		System.out.println("id :" + id);
		Optional<Product> p = productRepository.findById(id);
		Seller seller1 = sellerRepository.findByEmail(seller_email);
		if (seller1 != null) {
			String greetName;
			try {
				greetName = seller1.getName().substring(0, seller1.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = seller1.getName();
			}
			model.addAttribute("s_email", seller1.getEmail());
			model.addAttribute("greeting", greetName);
			model.addAttribute("add_check", seller1.getAddCheck());

		}
		if (!p.isEmpty()) {
			Product product = p.get();
			if (product != null) {

				seller1.getProducts().remove(product);
				System.out.println(seller1.getProducts());
				productRepository.delete(product);
				sellerRepository.save(seller1);

				/*
				 * if (seller1 != null) { String greetName = seller1.getName().substring(0,
				 * seller1.getName().indexOf(" ")); model.addAttribute("s_email",
				 * seller1.getEmail()); model.addAttribute("greeting", greetName); if
				 * (seller1.getProducts().isEmpty()) { model.addAttribute("p_list", null); }
				 * else { model.addAttribute("p_list",
				 * productRepository.findProductsBySeller(seller1)); }
				 * 
				 * }
				 */
			}

			/*
			 * else { model.addAttribute("login_credentials", "bad"); return "index"; }
			 */
		}
		if (seller1.getProducts().isEmpty()) {
			model.addAttribute("p_list", null);
		} else {
			if (((seller1.getProducts().size()) % 4) == 0) {
				model.addAttribute("length_check", "change");
			}
			model.addAttribute("p_list", productRepository.findProductsBySeller(seller1));
		}
		/*
		 * if (seller1.getServices().isEmpty()) { model.addAttribute("s_list", null); }
		 * else { model.addAttribute("s_list",
		 * serviceRepository.findServicesBySeller(seller1)); }
		 */
		model.addAttribute("p_added", "added");
		return "add_item";

	}

	/*
	 * @PostMapping("/add_service") public String processService(@ModelAttribute
	 * Service service, @RequestParam("seller_email") String s_email,
	 * 
	 * @RequestParam String qty_out, @RequestParam String
	 * adding, @RequestParam("main_image") MultipartFile file,
	 * 
	 * @RequestParam("sub_image") Optional<MultipartFile[]> files, Model model) {
	 * System.out.println(s_email); System.out.println(qty_out);
	 * System.out.println(adding);
	 * 
	 * try { Seller seller = sellerRepository.findByEmail(s_email); if
	 * (!seller.getAddCheck().equals(adding)) { if (file.isEmpty()) {
	 * System.out.println("File is empty"); } else {
	 * 
	 * service.setMainImage(file.getOriginalFilename()); File saveFile = new
	 * ClassPathResource("/static/images").getFile(); Path path =
	 * Paths.get(saveFile.getAbsolutePath() + File.separator +
	 * file.getOriginalFilename()); Files.copy(file.getInputStream(), path,
	 * StandardCopyOption.REPLACE_EXISTING); System.out.println("Image uploaded");
	 * 
	 * } if (files.isPresent()) { for (MultipartFile f : files.get()) {
	 * service.getSubImages().add(f.getOriginalFilename()); File saveFile = new
	 * ClassPathResource("static/images").getFile(); Path path =
	 * Paths.get(saveFile.getAbsolutePath() + File.separator +
	 * f.getOriginalFilename()); Files.copy(f.getInputStream(), path,
	 * StandardCopyOption.REPLACE_EXISTING);
	 * System.out.println("Sub Image uploaded"); } } for (String f1 :
	 * service.getFeatures()) { System.out.println(f1); }
	 * 
	 * if (seller != null) { System.out.println("password" + seller.getPassword());
	 * 
	 * String greetName = seller.getName().substring(0,
	 * seller.getName().indexOf(" "));
	 * 
	 * 
	 * model.addAttribute("greeting", greetName); model.addAttribute("s_email",
	 * s_email); model.addAttribute("seller", seller);
	 * 
	 * } else { System.out.println("seller is null"); }
	 * 
	 * // product.setDescription(description);
	 * 
	 * if (!qty_out.equals("piece")) { service.setPrice(service.getPrice() + "/" +
	 * qty_out); }
	 * 
	 * // service.setSeller(seller); // seller.getServices().add(service);
	 * seller.setPassword(seller.getPassword()); seller.setAddCheck(adding);
	 * 
	 * sellerRepository.save(seller);
	 * System.out.println("product added successfully");
	 * System.out.println("Data : " + service); model.addAttribute("s_added",
	 * "added");
	 * 
	 * } String greetName; try { greetName = seller.getName().substring(0,
	 * seller.getName().indexOf(" ")); } catch (StringIndexOutOfBoundsException se)
	 * { greetName = seller.getName(); } model.addAttribute("greeting", greetName);
	 * model.addAttribute("s_email", s_email); model.addAttribute("seller", seller);
	 * // model.addAttribute("s_list", //
	 * serviceRepository.findServicesBySeller(sellerRepository.findByEmail(s_email))
	 * ); model.addAttribute("p_list",
	 * productRepository.findProductsBySeller(seller));
	 * model.addAttribute("add_check", seller.getAddCheck());
	 * 
	 * if (!seller.getServices().isEmpty()) { if (((seller.getServices().size()) %
	 * 3) == 0) { model.addAttribute("length_check1", "change"); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * session.setAttribute("message", new
	 * Message("Something went wrong !! Try Again", "danger"));
	 * 
	 * }
	 * 
	 * return "add_item"; }
	 * 
	 * @PostMapping("/remove_service") public String deleteService(@RequestParam
	 * long id, @RequestParam String seller_email, Model model) {
	 * System.out.println("id :" + id); // Optional<Service> s =
	 * serviceRepository.findById(id); // System.out.println(s);
	 * model.addAttribute("s_added", "added"); Seller seller1 =
	 * sellerRepository.findByEmail(seller_email); if (seller1 != null) { String
	 * greetName; try { greetName = seller1.getName().substring(0,
	 * seller1.getName().indexOf(" ")); } catch (StringIndexOutOfBoundsException se)
	 * { greetName = seller1.getName(); } model.addAttribute("s_email",
	 * seller1.getEmail()); model.addAttribute("greeting", greetName);
	 * model.addAttribute("add_check", seller1.getAddCheck());
	 * 
	 * }
	 * 
	 * if (!s.isEmpty()) { System.out.println("system"); Service service = s.get();
	 * if (service != null) { System.out.println("system2");
	 * seller1.getServices().remove(service);
	 * 
	 * serviceRepository.delete(service);
	 * 
	 * System.out.println(seller1.getServices()); sellerRepository.save(seller1);
	 * 
	 * 
	 * if (seller1 != null) { String greetName = seller1.getName().substring(0,
	 * seller1.getName().indexOf(" ")); model.addAttribute("s_email",
	 * seller1.getEmail()); model.addAttribute("greeting", greetName); if
	 * (seller1.getProducts().isEmpty()) { model.addAttribute("p_list", null); }
	 * else { model.addAttribute("p_list",
	 * productRepository.findProductsBySeller(seller1)); }
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * else { model.addAttribute("login_credentials", "bad"); return "index"; }
	 * 
	 * 
	 * if (seller1.getServices().isEmpty()) { model.addAttribute("s_list", null);
	 * System.out.println("system3"); } else { if (((seller1.getServices().size()) %
	 * 3) == 0) { model.addAttribute("length_check1", "change"); }
	 * model.addAttribute("s_list",
	 * serviceRepository.findServicesBySeller(seller1)); }
	 * 
	 * if (seller1.getProducts().isEmpty()) { model.addAttribute("p_list", null); }
	 * else { model.addAttribute("p_list", seller1.getProducts()); }
	 * 
	 * return "add_item"; }
	 */

	@GetMapping("/create_account")
	public String createAccount(Model model) {
		/*
		 * String command ="curl -u rzp_test_y84XqHSv5nPYer:aRNRG1XGthPYkdebPAGg0IWN " +
		 * "-X POST https://api.razorpay.com/v1/fund_accounts --data contact_id=hye68bh54enk&account_type=bankAccount"
		 * + "&bankAccount=[name=abc]"; //String[] commands = new String[] {}; +
		 * "-F '{\r\n" + "  \"contact_id\":\"cont_00000000000001\",\r\n" +
		 * "  \"account_type\":\"bankAccount\",\r\n" + "  \"bankAccount\":{\r\n" +
		 * "    \"name\":\"Gaurav Kumar\",\r\n" + "    \"ifsc\":\"HDFC0000053\",\r\n" +
		 * "    \"account_number\":\"765432123456789\"\r\n" + "  }\r\n" + "}'";
		 * 
		 * 
		 * try { Process process= Runtime.getRuntime().exec(command) ;
		 * System.out.println(process); String result = new BufferedReader( new
		 * InputStreamReader(process.getInputStream())) .lines()
		 * .collect(Collectors.joining("")); System.out.println(result);
		 * 
		 * } catch(Exception e) { e.printStackTrace(); }
		 */

		try {
			/*
			 * RazorpayClient razorpay = new RazorpayClient("rzp_test_y84XqHSv5nPYer",
			 * "aRNRG1XGthPYkdebPAGg0IWN"); JSONObject fundAccountRequest = new
			 * JSONObject(); fundAccountRequest.put("customer_id", "jijjljvlrjler");
			 * fundAccountRequest.put("account_type", "bankAccount"); JSONObject bankAccount
			 * = new JSONObject(); bankAccount.put("name", "Gaurav Kumar");
			 * bankAccount.put("account_number", "11214311215411"); bankAccount.put("ifsc",
			 * "HDFC0000053"); fundAccountRequest.put("bankAccount", bankAccount);
			 * FundAccount fundAccount = razorpay.fundAccount.create(fundAccountRequest);
			 * System.out.println("Fund Account :" + fundAccount);
			 */

			// Generated by curl-to-java: https://fivesmallq.github.io/curl-to-java

			/*
			 * URL url = new URL("https://api.razorpay.com/v2/accounts/acc_LWXFUBYnysbYFV");
			 * HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			 * httpConn.setRequestMethod("POST");
			 * 
			 * httpConn.setRequestProperty("Content-type", "application/json");
			 * 
			 * byte[] message =
			 * ("rzp_test_y84XqHSv5nPYer:aRNRG1XGthPYkdebPAGg0IWN").getBytes("UTF-8");
			 * String basicAuth = DatatypeConverter.printBase64Binary(message);
			 * httpConn.setRequestProperty("Authorization", "Basic " + basicAuth);
			 * 
			 * httpConn.setDoOutput(true); OutputStreamWriter writer = new
			 * OutputStreamWriter(httpConn.getOutputStream()); writer.
			 * write("{\n  \"legal_business_name\": \"Acme Corp V2\",\n \"account_type\":\"bankAccount\",\n  \"bankAccount\":{\n    \"name\":\"Gaurav Kumar\",\n    \"ifsc\":\"HDFC0000053\",\n    \"account_number\":\"765432123456789\"\n  },\n  \"profile\": {\n    \"addresses\": {\n      \"operation\": {\n        \"street1\": \"5071, Koramangala 6th block\",\n        \"street2\": \"Kormanagala\",\n        \"city\": \"Bengaluru\",\n        \"state\": \"Karnataka\",\n        \"postal_code\": 560047,\n        \"country\": \"IN\"\n      }\n    }\n  },\n  \"legal_info\": {\n    \"pan\": \"BAACL1234C\",\n    \"gst\": \"10AABCU9603R1ZM\"\n  }\n}"
			 * ); writer.flush(); writer.close(); httpConn.getOutputStream().close();
			 * 
			 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
			 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
			 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
			 * s.next() : ""; System.out.println(response);
			 */
			/*
			 * RazorpayClient razorpay = new RazorpayClient("rzp_test_y84XqHSv5nPYer",
			 * "aRNRG1XGthPYkdebPAGg0IWN");
			 * 
			 * String virtualId = "acc_OlFjrtRZpUyz8p";
			 * 
			 * JSONObject virtualRequest = new JSONObject(); List<Object> types = new
			 * ArrayList<>(); types.add("vpa"); virtualRequest.put("types", types);
			 * JSONObject vpa = new JSONObject(); vpa.put("description", "gaurikumari");
			 * JSONObject bankAccount = new JSONObject(); bankAccount.put("name",
			 * "Gaurav Kumar"); bankAccount.put("account_number", "11214311215411");
			 * bankAccount.put("ifsc", "HDFC0000053"); virtualRequest.put("bankAccount",
			 * bankAccount);
			 * 
			 * VirtualAccount virtualaccount =
			 * razorpay.virtualAccounts.addReceiver(virtualId, virtualRequest);
			 * System.out.println("Accont : "+virtualaccount);
			 */
			/*
			 * RazorpayClient razorpay = new RazorpayClient("rzp_test_y84XqHSv5nPYer",
			 * "aRNRG1XGthPYkdebPAGg0IWN");
			 * 
			 * JSONObject virtualRequest = new JSONObject(); List<Object> types = new
			 * ArrayList<>(); JSONObject typesParam = new JSONObject();
			 * types.add("bankAccount"); typesParam.put("types",types);
			 * virtualRequest.put("receivers",typesParam); virtualRequest.put(
			 * "description","Customer Identifier created for Raftar Soft");
			 * virtualRequest.put("customer_id","acc_OlFjrtRZpUyz8p");
			 * virtualRequest.put("close_by",1681615838); JSONObject notes = new
			 * JSONObject(); notes.put("project_name","Banking Software");
			 * virtualRequest.put("notes", notes);
			 * 
			 * VirtualAccount virtualaccount =
			 * razorpay.virtualAccounts.create(virtualRequest);
			 * System.out.println("Accont : "+virtualaccount);
			 */
			/*
			 * Unirest.setTimeouts(0, 0); com.mashape.unirest.http.HttpResponse<String>
			 * response = Unirest.post("https://staging.eko.in:25004/ekoapi/v1/pan/verify")
			 * .header("Content-Type", "application/x-www-form-urlencoded")
			 * .header("developer_key", "becbbce45f79c6f5109f848acd540567")
			 * .header("secret-key", "MC6dKW278tBef+AuqL/5rW2K3WgOegF0ZHLW/FriZQw=")
			 * .header("secret-key-timestamp", "1516705204593") .field("pan_number",
			 * "VBLPZ6447L") .field("purpose", "1") .field("initiator_id", "9962981729")
			 * .field("purpose_desc", "onboarding") .asString();
			 * System.out.println(response);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	@GetMapping("/delete_account")
	public String deleteAccount() {
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_u5jZubA75Ra06W", "1zJ7H0tGqp2DerpCSUFAGoua");
			String account_id = "acc_OvbMiL8kd9yyCn";
			Account account = razorpay.account.delete(account_id);
			System.out.println(account);
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	@GetMapping("/fetch_account")
	public String fetchAccount() {
		URL url = null;
		try {
			url = new URL("https://api.razorpay.com/v2/accounts/acc_OmJZKFxYNKy1IT");
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

		byte[] message = null;
		try {
			message = ("rzp_test_y84XqHSv5nPYer:aRNRG1XGthPYkdebPAGg0IWN").getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String basicAuth = DatatypeConverter.printBase64Binary(message);
		httpConn.setRequestProperty("Authorization", "Basic " + basicAuth);

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
		try {
			JSONObject jsonObject = new JSONObject(response);
			System.out.println("Id : " + jsonObject.getString("id"));
			System.out.println("Name : " + jsonObject.getString("contact_name"));
			JSONObject jsonObject2 = jsonObject.getJSONObject("profile");
			JSONObject jsonObject3 = jsonObject2.getJSONObject("addresses");
			JSONObject registeredResponse = jsonObject3.getJSONObject("registered");

			// Object registeredResponse = jsonObject2.get("registered");
			System.out.println("Address : " + registeredResponse.getString("street1") + ","
					+ registeredResponse.getString("street2") + ",");
			System.out
					.println(registeredResponse.getString("city") + "," + registeredResponse.getString("state") + ",");
			System.out.println("PIN : " + registeredResponse.getString("postal_code"));

			/*
			 * JSONObject myResponse = jsonObject.getJSONObject("id");
			 * System.out.println(myResponse);
			 */
			Seller seller = sellerRepository.findByEmail(jsonObject.getString("email"));
			if (seller != null) {
				seller.setAccountId(jsonObject.getString("id"));
				sellerRepository.save(seller);
				System.out.println("seller updated");
			} else {
				System.out.println("No such seller");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "index";
	}

	/*
	 * @GetMapping("/find_distance") public String findDistance() { String origin =
	 * "A 2B/184A,Ekta Appartments,Paschim Vihar,New Delhi"; String origin =
	 * "Jwalaheri Village,Paschim Vihar, Delhi"; String destination =
	 * "Jwalaheri Village,Paschim Vihar, Delhi";
	 * 
	 * DistanceMatrixRow[] rows =
	 * getDistance("AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk", origin, destination);
	 * JSONArray object = new JSONArray(rows); System.out.println(object); //
	 * System.out.println(rows.toStri); return "index"; }
	 * 
	 * public DistanceMatrixRow[] getDistance(String apiKey, String origin, String
	 * destination) {
	 * 
	 * DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(new
	 * GeoApiContext.Builder().apiKey(apiKey).build());
	 * 
	 * DistanceMatrix query =
	 * req.origins(origin).destinations(destination).mode(TravelMode.DRIVING)
	 * .avoid(RouteRestriction.TOLLS).language("en-US").awaitIgnoreError();
	 * 
	 * if (query != null && query.rows.length > 0) { return query.rows; } else {
	 * return null; }
	 * 
	 * }
	 */

	@GetMapping("/update_address")
	public String updateAddress() {
		Seller seller = sellerRepository.findByEmail("budhakumar21@gmail.com");
		/*
		 * String address = seller.getStreet1() + "," + seller.getStreet2() + "," +
		 * seller.getCity() + "," + seller.getState();
		 */
		String address = "Narengi,Assam";
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
		System.out.println(address2);
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/place/queryautocomplete/json?input=" + address2
					+ "&key=" + api_key);
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
		try {
			JSONObject myResponse = new JSONObject(response);
			JSONArray predictions = (JSONArray) myResponse.get("predictions");
			List<String> desciptions = new ArrayList<>();
			for (int i = 0; i < predictions.length(); i++) {
				desciptions.add(predictions.getJSONObject(i).getString("description"));
			}
			String choosenAddress = "";
			for (String description : desciptions) {
				if (description.length() > choosenAddress.length())
					choosenAddress = description;

			}
			System.out.println("Address : " + choosenAddress);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// System.out.println(description);
		// System.out.println(response);
		return "index";
	}

	@GetMapping("/get_customer")
	public String getCustomer() {
		URL url = null;
		try {
			url = new URL("https://api.ipify.org?format=json");
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
		try {
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			// System.out.println(response);
			JSONObject jsonObject = new JSONObject(response);
			String ip = jsonObject.getString("ip");
			// System.out.println(ip);
			String response1 = requestFurther(ip);
			// System.out.println(response1);
			JSONObject jsonObject1 = new JSONObject(response1);
			double latitude = Double.parseDouble(jsonObject1.get("latitude").toString());
			double longitude = Double.parseDouble(jsonObject1.get("longitude").toString());

			String response2 = requestFurther1(28.688384, 77.1719168);
			System.out.println(response2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// requestLiveLocation();
		return "index";
	}

	public String requestFurther1(double latitude, double longitude) {
		String api_key = "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk";
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude
					+ "&key=" + api_key);
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
		return response;
	}

	public String requestFurther(String ip) {
		String accessKey = "d5c77e11-409f-46d8-9f23-c1fc9de595cf";
		URL url = null;
		try {
			url = new URL("https://apiip.net/api/check?ip=" + ip + "&accessKey=" + accessKey);
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
		return response;
	}

	public void requestLiveLocation() {
		try {
			/*
			 * URL url = new URL(
			 * "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk"
			 * ); HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			 * httpConn.setRequestMethod("POST");
			 * 
			 * httpConn.setRequestProperty("Content-Type", "application/json");
			 * 
			 * httpConn.setDoOutput(true); OutputStreamWriter writer = new
			 * OutputStreamWriter(httpConn.getOutputStream()); writer.write(
			 * "{ \"homeMobileCountryCode\":404,\n   \"homeMobileNetworkCode\":2,\n   \"radioType\":\"gsm\",\n   \"carrier\":\"Airtel\",\n   \"considerIp\":true\n}"
			 * ); writer.flush(); writer.close(); httpConn.getOutputStream().close();
			 * 
			 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
			 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
			 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
			 * s.next() : ""; System.out.println(response);
			 */
			URL url = new URL(
					"https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			httpConn.setRequestProperty("Content-Type", "application/json");

			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(
					"{ \"homeMobileCountryCode\":404,\n   \"homeMobileNetworkCode\":2,\n   \"radioType\":\"NR\",\n   \"carrier\":\"Airtel\",\n   \"considerIp\":true,\n   \"cellTowers\": [\n    {\n      \"newRadioCellId\": 68719476735,\n      \"mobileCountryCode\": 404,\n      \"mobileNetworkCode\": 2,\n      \"age\": 0,\n      \"signalStrength\": -60,\n    }\n  ],\n  \"wifiAccessPoints\": [\n    {\n      \"macAddress\": \"3c:37:86:5d:75:d4\",\n      \"signalStrength\": -35,\n      \"signalToNoiseRatio\": 0\n    },\n    {\n      \"macAddress\": \"4C-03-4F-4B-43-6C\",\n      \"signalStrength\": -35,\n      \"signalToNoiseRatio\": 0\n    }\n  ]\n}");
			writer.flush();
			writer.close();
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
					: httpConn.getErrorStream();
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/send_sms")
	public String SendSms() {
		/*
		 * SendSms.init();
		 * 
		 * SendSms.server = "http://127.0.0.1:8800/";
		 * 
		 * SendSms.user = "Easy Query";
		 * 
		 * SendSms.password = "iam2578legend";
		 * 
		 * SendSms.phonenumber = "+918527508751";
		 * 
		 * SendSms.text = "This is a test message";
		 * 
		 * //SendSms.wapurl = "https://nowsms.com/";
		 * 
		 * SendSms.send();
		 */
		try {

			// AsyncHttpClient client = new DefaultAsyncHttpClient();
			/*
			 * AsyncHttpClient client = new DefaultAsyncHttpClient(); client.prepare("POST",
			 * "https://sms77io.p.rapidapi.com/sms") .setHeader("x-rapidapi-key",
			 * "MzM3NDc5NTE1MTcyNmU2NDY4Nzc0NDRkNTI2OTYyNTk=") .setHeader("x-rapidapi-host",
			 * "sms77io.p.rapidapi.com") .setHeader("Content-Type",
			 * "multipart/form-data; boundary=---011000010111000001101001") .setBody(
			 * "-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"to\"\r\n\r\n+918527508751\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"text\"\r\n\r\nDear customer. We want to say thanks for your trust. Use code MINUS10 for 10 % discount on your next order!\r\n-----011000010111000001101001--\r\n\r\n"
			 * ) .execute().toCompletableFuture().thenAccept(System.out::println).join();
			 * 
			 * client.close();
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 * 
			 * /* try { // Construct data AsyncHttpClient client = new
			 * DefaultAsyncHttpClient(); client.prepare("POST",
			 * "https://sms77io.p.rapidapi.com/sms") .setHeader("x-rapidapi-key",
			 * "e46aaa9fcdmsh432aef779dcf2dep1e7fccjsnc7d818970504")
			 * .setHeader("x-rapidapi-host", "sms77io.p.rapidapi.com")
			 * .setHeader("Content-Type",
			 * "multipart/form-data; boundary=---011000010111000001101001")
			 * .setBody("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"to\"\r\n\r\n+918527508751\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"text\"\r\n\r\nDear customer. We want to say thanks for your trust. Use code MINUS10 for 10 % discount on your next order!\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"label\"\r\n\r\nEasy_Query\r\n-----011000010111000001101001--\r\n\r\n"
			 * ) .execute() .toCompletableFuture() .thenAccept(System.out::println) .join();
			 * 
			 * client.close(); //return stringBuffer.toString(); } catch (Exception e) {
			 * System.out.println("Error SMS "+e); //return "Error "+e; }
			 */
			/*
			 * URL url = new URL("https://dkqxqg.api.infobip.com/messages-api/1/messages");
			 * HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			 * httpConn.setRequestMethod("POST");
			 * 
			 * httpConn.setRequestProperty("Authorization",
			 * "App 7dc36cac657fe129e51f1040de1bff4b-ad4c0ae9-37cc-48f8-83d9-704c0ba1663e");
			 * httpConn.setRequestProperty("Content-Type", "application/json");
			 * httpConn.setRequestProperty("Accept", "application/json");
			 * 
			 * httpConn.setDoOutput(true); OutputStreamWriter writer = new
			 * OutputStreamWriter(httpConn.getOutputStream()); writer.
			 * write("{\n  \"messages\": [\n    {\n      \"channel\": \"SMS\",\n      \"sender\": \"Easy Query\",\n      \"destinations\": [\n        {\n          \"to\": \"918527508751\"\n        }\n      ],\n      \"content\": {\n        \"body\": {\n          \"text\": \"May the Force be with you.\",\n          \"type\": \"TEXT\"\n        }\n      }\n    }\n  ]\n}"
			 * ); writer.flush(); writer.close(); httpConn.getOutputStream().close();
			 * 
			 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
			 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
			 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
			 * s.next() : ""; System.out.println(response);
			 */
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://sms77io.p.rapidapi.com/sms"))
					.header("x-rapidapi-key", "e46aaa9fcdmsh432aef779dcf2dep1e7fccjsnc7d818970504")
					.header("x-rapidapi-host", "sms77io.p.rapidapi.com")
					.header("Content-Type", "multipart/form-data; boundary=---011000010111000001101001")
					.method("POST", HttpRequest.BodyPublishers.ofString(
							"-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"to\"\r\n\r\n+918527508751\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"text\"\r\n\r\nDear customer. We want to say thanks for your trust. Use code MINUS10 for 10 % discount on your next order!\r\n-----011000010111000001101001--\r\n\r\n"))
					.build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "index";
		}

	}

	@GetMapping("/verify")
	// @ResponseBody
	public String verification() {

		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(
				"https://id.sinch.com/u/login/identifier?state=hKFo2SBQcS1DcW0zWjdocHBNa08tOUswRU1sWnZCa2J2eE95RaFur3VuaXZlcnNhbC1sb2dpbqN0aWTZIC1vZGVxdUVqcTFHQ1VJbnJ4M0R1a3JRN0ZrekpUZm9po2NpZNkgU0xJVTIwWFd0bXo5dVFrWHBVUDE3RUtNQlN4U05Nd1o");

		// WebElement email =
		// driver.findElement(By.xpath("//input[@inputmode='email']"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement email = driver.findElement(By.id("username"));
		email.sendKeys("budhakumar21@gmail.com");
		// WebElement password = driver.findElement(By.id("password"));
		WebElement verifyBtn = driver.findElement(By.className("_button-login-id"));
		verifyBtn.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> tabs = new ArrayList<>();
		// driver.switchTo().window(tabs.get(1));
		WebElement continueBtn = driver.findElement(By.className("_button-login-password"));
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Iam2578legend@sKumar22");
		continueBtn.click();

		// driver.close();
		/*
		 * while(true) {
		 * 
		 * }
		 */

		/*
		 * String servicePlanId = "2b75ca266f654d1592316d401ddc50b1"; String token =
		 * "4009baf0ca2046b980f45f5e613865b2"; ApiConnection conn =
		 * ApiConnection.builder().servicePlanId(servicePlanId).token(token).start();
		 * String sender = "SENDER"; // Optional, must be valid phone number, short code
		 * or alphanumeric. String[] recipients = { "+919205080980" }; try {
		 * MtBatchTextSmsResult batch = conn.createBatch(
		 * SinchSMSApi.batchTextSms().sender(sender).addRecipient(recipients).body("Mum"
		 * ).build()); } catch (InterruptedException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (ApiException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		return "index";
	}

	@PostMapping("/go-to-seller")
	public String goToSeller(@RequestParam String email, Model model) {
		Seller seller1 = sellerRepository.findByEmail(email);
		/*
		 * model.addAttribute("customer", new Customer());
		 * model.addAttribute("bankAccount", new BankAccount());
		 */
		if (seller1 != null) {
			String greetName;
			try {
				greetName = seller1.getName().substring(0, seller1.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = seller1.getName();
			}
			model.addAttribute("s_email", seller1.getEmail());
			model.addAttribute("greeting", greetName);
			model.addAttribute("add_check", seller1.getAddCheck());

			// model.addAttribute("backB", "true");
			// model.addAttribute("seller", new Seller());
			model.addAttribute("p_list",
					productRepository.findProductsBySeller(sellerRepository.findByEmail(seller1.getEmail())));
			/*
			 * model.addAttribute("s_list",
			 * serviceRepository.findServicesBySeller(sellerRepository.findByEmail(seller1.
			 * getEmail())));
			 */
			if (seller1.getProducts().isEmpty()) {
				model.addAttribute("p_list", null);
			} else {
				if (((seller1.getProducts().size()) % 4) == 0) {
					model.addAttribute("length_check", "change");
				}
			}
			/*
			 * if (seller1.getServices().isEmpty()) { model.addAttribute("s_list", null); }
			 * else { if (((seller1.getServices().size()) % 3) == 0) {
			 * model.addAttribute("length_check1", "change"); } }
			 */
		} /*
			 * else { model.addAttribute("login_credentials", "bad"); //
			 * model.addAttribute("backB", "false"); return "login_form"; }
			 */
		System.out.println("size:" + seller1.getProducts().size());
		return "add_item";

	}
	/*
	 * public String getCoordinates() { OkHttpClient client = new
	 * OkHttpClient().newBuilder() .build(); Request.Builder request = new
	 * Builder<T>() { }; .url(
	 * "https://api.geoapify.com/v1/geocode/search?text=38%20Upper%20Montagu%20Street%2C%20Westminster%20W1H%201LJ%2C%20United%20Kingdom&apiKey=d90e1a844a7947afa56a99320d10c353")
	 * .method("GET", null) .build(); Response response =
	 * client.newCall(request).execute(); }
	 */
}