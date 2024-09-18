package com.shopping_centre.controller;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.shopping_centre.dao.BankAccountRepository;
import com.shopping_centre.dao.CustomerItemRepository;
import com.shopping_centre.dao.CustomerRepository;
import com.shopping_centre.dao.OtpDetailsRepository;
import com.shopping_centre.dao.ProductRepository;
import com.shopping_centre.dao.SellerRepository;
import com.shopping_centre.entities.BankAccount;
import com.shopping_centre.entities.Customer;
import com.shopping_centre.entities.OtpDetails;
import com.shopping_centre.entities.Seller;
import com.shopping_centre.service.EmailService;

import jakarta.xml.bind.DatatypeConverter;

@Controller
//@CrossOrigin(origins = "*")
@RequestMapping("/verify")
public class VerificationController {
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private OtpDetailsRepository otpDetailsRepository;
	@Autowired
	private CustomerRepository customerRepository;
	/*
	 * @Autowired private ServiceRepository serviceRepository;
	 */
	/*
	 * @Autowired private BankAccountRepository bankAccountRepository;
	 */

	@PostMapping("/otp-to-mail")
	public String sendOtpToMail(@RequestParam String email, Model model) {
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		String message = "<div style='border:5px solid red;padding:20px'><b>OTP: " + otp + "</b></div>";
		String emailOriginal = email.substring(email.indexOf(":") + 1);
//		+ "</b><hr/>" + "<b>Email: " + registration.getEmail() + "</b><hr/>" + "<b>Phone: "
//		+ registration.getPhone() + "</b><hr/>" +
//		"<b>Age: "
//		+ registration.getAge() + "</b><hr/>" +
//		"<b>Address: "
//		+ registration.getAddress() + "</b><hr/>" +
//		"<b>City: "
//		+ registration.getCity() + "</b><hr/>" +
//		"<b>State: "
//		+ registration.getState() + "</b><hr/>" +
//		"<b>PIN: "
//		+ registration.getPinCode() + "</b><hr/>" +
//
//		"</div>";
		String subject = "OTP from Easy Query";
		String to = emailOriginal;
		String from = "mcoder70@gmail.com";
		boolean mailSent = emailService.sendEmail(message, subject, to, from);
		OtpDetails otpDetails = otpDetailsRepository.findByEmail(email);
		if (otpDetails == null) {
			otpDetails = new OtpDetails();
			otpDetails.setEmail(emailOriginal);
			otpDetails.setOtp(String.valueOf(otp));
		} else {
			otpDetails.setOtp(String.valueOf(otp));
		}
		otpDetailsRepository.save(otpDetails);
		System.out.println(mailSent);
		System.out.println(otp);
		if (email.contains("seller"))
			model.addAttribute("s_email", emailOriginal);
		else
			model.addAttribute("c_email", emailOriginal);
		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		model.addAttribute("mail_otp", "otp");
		return "login_form";
	}

	@PostMapping("/verify-otp-mail")
	public String verifyOtpMail(@RequestParam String email, @RequestParam String otp, Model model) {
		String emailOriginal = email.substring(email.indexOf(":") + 1);
		OtpDetails otpDetails = otpDetailsRepository.findByEmail(emailOriginal);
		if (otpDetails.getOtp().equals(otp)) {
			System.out.println("matched");
			otpDetailsRepository.delete(otpDetails);
			if (email.contains("seller")) {
				Seller seller = sellerRepository.findByEmail(emailOriginal);
				seller.setEmailVerification(true);
				sellerRepository.save(seller);
				// model.addAttribute("s_email", emailOriginal);
				model.addAttribute("ma_verification", true);
				model.addAttribute("mo_verification", seller.isMobileVerification());
				if (seller.isMobileVerification()) {

					try {
						URL url = new URL("https://api.razorpay.com/v2/accounts");
						HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
						httpConn.setRequestMethod("POST");

						httpConn.setRequestProperty("Content-type", "application/json");

						byte[] message = ("rzp_test_u5jZubA75Ra06W:1zJ7H0tGqp2DerpCSUFAGoua").getBytes("UTF-8");
						String basicAuth = DatatypeConverter.printBase64Binary(message);
						httpConn.setRequestProperty("Authorization", "Basic " + basicAuth);

						httpConn.setDoOutput(true);
						System.out.println("check :" + seller.getEmail());
						String mail = seller.getEmail();
						String phone = seller.getPhone();
						String bussinessType;
						if (seller.getBussinessType().contains(" ")) {
							bussinessType = seller.getBussinessType().replace(" ", "_").toLowerCase();
						} else {
							bussinessType = seller.getBussinessType().toLowerCase();
						}
						String referenceId = "Easy_Query" + seller.getId();
						OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

						writer.write(String.format("{\n   \"email\":\"%s\",\n   \"phone\":\"%s\","
								+ "\n   \"type\":\"route\",\n   \"reference_id\":\"%s\","
								+ "\n   \"legal_business_name\":\"%s\",\n   \"business_type\":\"%s\","
								+ "\n   \"contact_name\":\"%s\","
								+ "\n   \"profile\":{\n      \"category\":\"healthcare\",\n      \"subcategory\":\"clinic\","
								+ "\n      \"addresses\":{\n         \"registered\":{"
								+ "\n            \"street1\":\"%s\","
								+ "\n            \"street2\":\"street2\",\n            \"city\":\"%s\","
								+ "\n            \"state\":\"%s\",\n            \"postal_code\":\"110045\","
								+ "\n            \"country\":\"IN\"\n         }\n      }\n   },\n   \"legal_info\":{"
								+ "\n      \"pan\":\"AAACL1234C\",\n      \"gst\":\"18AABCU9603R1ZM\"\n   }\n}", mail,
								phone, referenceId, seller.getBussinessName(), bussinessType, seller.getName(),
								seller.getStreet1(), seller.getCity(), seller.getState()));

						writer.flush();
						writer.close();
						httpConn.getOutputStream().close();

						InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
								: httpConn.getErrorStream();
						Scanner s = new Scanner(responseStream).useDelimiter("\\A");
						String response = s.hasNext() ? s.next() : "";
						System.out.println(response);
						if (response.contains("error")) {
							sellerRepository.delete(seller);
							JSONObject jsonObject = new JSONObject(response);
							JSONObject jsonObject2 = jsonObject.getJSONObject("error");
							String error = jsonObject2.getString("description");
							System.out.println(error);
							model.addAttribute("mobileError", error);
							model.addAttribute("credentials", "bad");
							model.addAttribute("seller", seller);
							return "login_form";
						} else {
							try {
								sellerRepository.save(seller);
							} catch (DataIntegrityViolationException e) {
								model.addAttribute("cvError", "This email is already registered");
								model.addAttribute("seller", seller);
								model.addAttribute("credentials", "bad");
								return "login_form";
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} /*
				 * else { Customer customer = customerRepository.findByPhone(emailOriginal);
				 * customer.setEmailVerification(true); customerRepository.save(customer);
				 * model.addAttribute("ma_verification", true);
				 * model.addAttribute("mo_verification", customer.isMobileVerification()); //
				 * model.addAttribute("c_email", emailOriginal); }
				 */
			model.addAttribute("credentials", "email_verified");

		} else {
			System.out.println("mis_matched");
			model.addAttribute("credentials", "email_mismatched");
		}
		/*
		 * otpDetailsRepository.save(otpDetails); System.out.println(mailSent);
		 * System.out.println(otp);
		 */
		if (email.contains("seller"))
			model.addAttribute("s_email", emailOriginal);
		else
			model.addAttribute("c_email", emailOriginal);
		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		// model.addAttribute("mail_otp","otp");
		return "login_form";
	}

	@PostMapping("/otp-to-mobile")
	public String sendOtpToMobile(@RequestParam String email, Model model) {
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		String emailOriginal = email.substring(email.indexOf(":") + 1);
		String mobile = null;
		if (email.contains("seller")) {
			mobile = sellerRepository.findByEmail(emailOriginal).getPhone();
			model.addAttribute("s_email", emailOriginal);
		} else {
			mobile = customerRepository.findByPhone(emailOriginal).getPhone();
			model.addAttribute("c_email", emailOriginal);
			System.out.println(mobile);
		}

		// String message = "<div style='border:5px solid red;padding:20px'><b>OTP: " +
		// otp + "</b></div>";
//		+ "</b><hr/>" + "<b>Email: " + registration.getEmail() + "</b><hr/>" + "<b>Phone: "
//		+ registration.getPhone() + "</b><hr/>" +
//		"<b>Age: "
//		+ registration.getAge() + "</b><hr/>" +
//		"<b>Address: "
//		+ registration.getAddress() + "</b><hr/>" +
//		"<b>City: "
//		+ registration.getCity() + "</b><hr/>" +
//		"<b>State: "
//		+ registration.getState() + "</b><hr/>" +
//		"<b>PIN: "
//		+ registration.getPinCode() + "</b><hr/>" +
//
//		"</div>";
		/*
		 * String subject = "OTP from Easy Query"; String to = email; String from =
		 * "mcoder70@gmail.com"; boolean mailSent = emailService.sendEmail(message,
		 * subject, to, from);
		 */

		try {
			URL url = new URL("https://www.fast2sms.com/dev/bulkV2");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			httpConn.setRequestProperty("authorization",
					"tlSRwnXvvicdGrlFCsnWG0sE6gn2AOIF8Z9hZEKuzLakuYdZX3yP19ctDwul");
			httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write("variables_values=" + otp + "&route=otp&numbers=" + mobile);
			writer.flush();
			writer.close();
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
					: httpConn.getErrorStream();
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			System.out.println(response);
			System.out.println(response);
		} catch (Exception e) { // TODO Auto-generated catch block
			System.out.println("eror");
			e.printStackTrace();
		}

		OtpDetails otpDetails = otpDetailsRepository.findByEmail(mobile);
		if (otpDetails == null) {
			otpDetails = new OtpDetails();
			otpDetails.setEmail(mobile);
			otpDetails.setOtp(String.valueOf(otp));
		} else {
			otpDetails.setOtp(String.valueOf(otp));
		}
		otpDetailsRepository.save(otpDetails);
		// System.out.println(mailSent);
		System.out.println(otp);

		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		model.addAttribute("mobile_otp", "otp");
		model.addAttribute("mo_verification", false);

		if (email.contains("seller"))
			return "login_form";
		else
			return "customer_index";

	}

	@PostMapping("/verify-otp-mobile")
	public String verifyOtpMobile(@RequestParam String email, @RequestParam String otp, Model model) {
		System.out.println("eemail:" + email);
		String emailOriginal = email.substring(email.indexOf(":") + 1);
		String mobile = null;
		// OtpDetails otpDetails = otpDetailsRepository.findByEmail(mobile);

		if (email.contains("seller")) {
			mobile = sellerRepository.findByEmail(emailOriginal).getPhone();
			OtpDetails otpDetails = otpDetailsRepository.findByEmail(mobile);
			if (otpDetails.getOtp().equals(otp)) {
				System.out.println("matched");
				otpDetailsRepository.delete(otpDetails);
				Seller seller = sellerRepository.findByEmail(emailOriginal);
				seller.setMobileVerification(true);
				sellerRepository.save(seller);
				model.addAttribute("credentials", "mobile_verified");
				model.addAttribute("mo_verification", true);
				model.addAttribute("ma_verification", seller.isEmailVerification());
				if (seller.isEmailVerification()) {
					/*
					 * try { URL url = new URL("https://api.razorpay.com/v2/accounts");
					 * HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
					 * httpConn.setRequestMethod("POST");
					 * 
					 * httpConn.setRequestProperty("Content-type", "application/json");
					 * 
					 * byte[] message =
					 * ("rzp_test_u5jZubA75Ra06W:1zJ7H0tGqp2DerpCSUFAGoua").getBytes("UTF-8");
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
					 * OutputStreamWriter(httpConn.getOutputStream());
					 * 
					 * writer.write(String.format("{\n   \"email\":\"%s\",\n   \"phone\":\"%s\"," +
					 * "\n   \"type\":\"route\",\n   \"reference_id\":\"%s\"," +
					 * "\n   \"legal_business_name\":\"%s\",\n   \"business_type\":\"%s\"," +
					 * "\n   \"contact_name\":\"%s\"," +
					 * "\n   \"profile\":{\n      \"category\":\"healthcare\",\n      \"subcategory\":\"clinic\","
					 * + "\n      \"addresses\":{\n         \"registered\":{" +
					 * "\n            \"street1\":\"%s\"," +
					 * "\n            \"street2\":\"street2\",\n            \"city\":\"%s\"," +
					 * "\n            \"state\":\"%s\",\n            \"postal_code\":\"110045\"," +
					 * "\n            \"country\":\"IN\"\n         }\n      }\n   },\n   \"legal_info\":{"
					 * +
					 * "\n      \"pan\":\"AAACL1234C\",\n      \"gst\":\"18AABCU9603R1ZM\"\n   }\n}"
					 * , mail, phone, referenceId, seller.getBussinessName(), bussinessType,
					 * seller.getName(), seller.getCity(), seller.getCity(), seller.getState()));
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
					 * try { JSONObject jsonObject = new JSONObject(response); String id =
					 * jsonObject.getString("id"); seller.setAccountId(id);
					 * sellerRepository.save(seller); } catch (DataIntegrityViolationException e) {
					 * model.addAttribute("cvError", "This email is already registered");
					 * model.addAttribute("seller", seller); model.addAttribute("credentials",
					 * "bad"); return "login_form"; } } } catch (Exception e) { e.printStackTrace();
					 * }
					 */
				}
			} else {
				System.out.println("mis_matched");
				model.addAttribute("credentials", "mobile_mismatched");
			}
			model.addAttribute("bankAccount", new BankAccount());
		} else {
			mobile = customerRepository.findByPhone(emailOriginal).getPhone();
			OtpDetails otpDetails = otpDetailsRepository.findByEmail(mobile);
			if (otpDetails != null) {
				if (otpDetails.getOtp().equals(otp)) {
					System.out.println("matched");
					otpDetailsRepository.delete(otpDetails);
					Customer customer = customerRepository.findByPhone(emailOriginal);
					customer.setMobileVerification(true);
					customerRepository.save(customer);
					model.addAttribute("credentials3", "mobile verified");
					model.addAttribute("mo_verification", true);
					model.addAttribute("ma_verification", true);

				} else {
					System.out.println("mis_matched");
					model.addAttribute("credentials3", "mobile mismatched");
				}
				model.addAttribute("logged_in", true);
				model.addAttribute("c_email", mobile);
				// model.addAttribute("logged_in",true);
				System.out.println("mobil" + mobile);
			}

			model.addAttribute("customer", new Customer());
			return "customer_index";
		}

		/*
		 * else { System.out.println("mis_matched"); model.addAttribute("credentials",
		 * "mobile_mismatched"); }
		 */
		/*
		 * otpDetailsRepository.save(otpDetails); System.out.println(mailSent);
		 * System.out.println(otp);
		 */
		// model.addAttribute("s_email", email);
		System.out.println("eoriginal" + emailOriginal);
		if (email.contains("seller"))
			model.addAttribute("s_email", emailOriginal);
		else
			model.addAttribute("c_email", emailOriginal);
		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		// model.addAttribute("mail_otp","otp");
		return "login_form";
	}

}
