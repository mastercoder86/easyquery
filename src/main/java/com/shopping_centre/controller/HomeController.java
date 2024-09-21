package com.shopping_centre.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/*import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.phonepe.sdk.pg.Env;
import com.phonepe.sdk.pg.common.http.PhonePeResponse;
import com.phonepe.sdk.pg.payments.v1.PhonePePaymentClient;
import com.phonepe.sdk.pg.payments.v1.models.request.PgPayRequest;
import com.phonepe.sdk.pg.payments.v1.models.response.PayPageInstrumentResponse;
import com.phonepe.sdk.pg.payments.v1.models.response.PgPayResponse;
import com.shopping_centre.entities.BankAccount;
import com.shopping_centre.entities.Customer;
import com.shopping_centre.entities.OtpDetails;
import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;

@Controller
public class HomeController {

	/*
	 * = null;
	 * 
	 * // @Autowired /* private ProductRepository productRepository;
	 * 
	 * @Autowired private ServiceRepository serviceRepository;
	 * 
	 * @Autowired
	 */
	// private CustomerRepository customerRepository;

	@GetMapping("/")
	public String home(Model model) {
		Seller seller = new Seller();
		model.addAttribute("seller", seller);
		model.addAttribute("customer", new Customer());
		model.addAttribute("bankAccount", new BankAccount());
		return "customer_index";
	}

	/*
	 * @GetMapping("/show_cat_items") public String showCatItems(@RequestParam
	 * String cat_name, @RequestParam String page,
	 * 
	 * @RequestParam Optional<String> c_email, Model model) { List<Product> products
	 * = productRepository.findByCategory(cat_name); List<Service> services =
	 * serviceRepository.findByCategory(cat_name); model.addAttribute("p_list1",
	 * products); model.addAttribute("s_list1", services);
	 * model.addAttribute("category", cat_name); model.addAttribute("seller", new
	 * Seller()); model.addAttribute("show", "show"); if (!(c_email.isEmpty())) {
	 * model.addAttribute("c_email", c_email.get()); Customer customer =
	 * customerRepository.findByEmail(c_email.get()); String greetName; try {
	 * greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
	 * } catch (StringIndexOutOfBoundsException se) { greetName =
	 * customer.getName(); } model.addAttribute("greeting", greetName); } if
	 * (products != null) { if (products.isEmpty()) { model.addAttribute("p_list1",
	 * null); } } if (services != null) { if (services.isEmpty()) {
	 * model.addAttribute("s_list1", null); } } System.out.println("page :" + page);
	 * if (page.equals("index")) return "index"; else return "customer_index";
	 * 
	 * }
	 * 
	 * @GetMapping("/show_product") public String showProduct(@RequestParam long
	 * id, @RequestParam String page, @RequestParam Optional<String> c_email, Model
	 * model) { Product product = productRepository.findById(id).get();
	 * model.addAttribute("product", product); model.addAttribute("seller", new
	 * Seller()); model.addAttribute("show", "show"); model.addAttribute("category",
	 * null); if (!(c_email.isEmpty())) { model.addAttribute("c_email",
	 * c_email.get()); Customer customer =
	 * customerRepository.findByEmail(c_email.get()); String greetName; try {
	 * greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
	 * } catch (StringIndexOutOfBoundsException se) { greetName =
	 * customer.getName(); } model.addAttribute("greeting", greetName); } if
	 * (page.equals("index")) return "index"; else return "customer_index"; }
	 * 
	 * @GetMapping("/show_service") public String showService(@RequestParam long
	 * id, @RequestParam String page,@RequestParam Optional<String> c_email, Model
	 * model) { System.out.println("shows0"); Service service =
	 * serviceRepository.findById(id).get(); model.addAttribute("service", service);
	 * model.addAttribute("seller", new Seller()); model.addAttribute("show",
	 * "show"); model.addAttribute("category", null); if(!(c_email.isEmpty())) {
	 * model.addAttribute("c_email",c_email.get()); Customer customer =
	 * customerRepository.findByEmail(c_email.get()); String greetName; try {
	 * greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
	 * } catch (StringIndexOutOfBoundsException se) { greetName =
	 * customer.getName(); } model.addAttribute("greeting", greetName); } if
	 * (page.equals("index")) return "index";
	 * 
	 * else return "customer_index"; }
	 */
	@GetMapping("/login-form")
	public String loginForm(Model model) {
		model.addAttribute("bankAccount", new BankAccount());
		model.addAttribute("seller", new Seller());
		model.addAttribute("customer", new Customer());
		return "login_form";
	}

	/*
	 * @GetMapping("/search/{query}") public ResponseEntity<List<Product>>
	 * search(@PathVariable String query){ List<Product> products =
	 * productRepository.findByName(query); return ResponseEntity.ok(products); }
	 */

	/*
	 * @GetMapping("/search-address/{query}/{pinCode}") public
	 * ResponseEntity<List<String>> updateAddress(@PathVariable String
	 * query, @PathVariable String pinCode) { // Seller seller =
	 * sellerRepository.findByEmail("budhakumar21@gmail.com");
	 * 
	 * String address = seller.getStreet1() + "," + seller.getStreet2() + "," +
	 * seller.getCity() + "," + seller.getState();
	 * 
	 * String address = query + ",New Delhi-" + pinCode; String address1 = null;
	 * String address3 = null; String api_key =
	 * "AIzaSyCl30do4Fl45eUvhk6PQDFA7JMWIBhS0jk"; if (address.contains("  ")) {
	 * address1 = address.replace("  ", " ");
	 * 
	 * } String address2 = null; if (address1 != null) address2 =
	 * address1.replace(" ", "%20").replace(",", "%2C%20"); else address2 =
	 * address.replace(" ", "%20").replace(",", "%2C%20"); address3 = address2 +
	 * "%2C%20New Delhi"; System.out.println(address2);
	 * System.out.println(address3);
	 * 
	 * URL url = null; try { url = new URL(
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
	 * }
	 * 
	 * System.out.println("Address : " + choosenAddress); //
	 * System.out.println(description); // System.out.println(response); return
	 * ResponseEntity.ok(desciptions);
	 * 
	 * } catch (JSONException e) { e.printStackTrace(); return
	 * ResponseEntity.ok(null); }
	 * 
	 * }
	 */
	/*
	 * @GetMapping("/login-form") public String loginForm() { return "login_form"; }
	 */
	@GetMapping("/phonepe-payment")
	public String phonePePayment(Model model) throws IOException {

		/*
		 * URL url = null; try { url = new URL(
		 * "https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay?request=ewoibWVyY2hhbnRJZCI6ICJQR1RFU1RQQVlVQVQ4NiIsCiAgIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6ICJNVDc4NTA1OTAwNjgxODgxMDQiLAogICJtZXJjaGFudFVzZXJJZCI6ICJNVUlEMTIzIiwKICAiYW1vdW50IjogMTAwLAogICJyZWRpcmVjdFVybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9yZWRpcmVjdC11cmwiLAogICJyZWRpcmVjdE1vZGUiOiAiUkVESVJFQ1QiLAogICJjYWxsYmFja1VybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9jYWxsYmFjay11cmwiLAogICJtb2JpbGVOdW1iZXIiOiAiOTk5OTk5OTk5OSIsCiAgInBheW1lbnRJbnN0cnVtZW50IjogewogICAgInR5cGUiOiAiUEFZX1BBR0UiCiAgfQp9"
		 * ); } catch (MalformedURLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } HttpURLConnection httpConn = (HttpURLConnection)
		 * url.openConnection(); httpConn.setRequestMethod("POST");
		 * 
		 * 
		 * 
		 * httpConn.setRequestProperty("X-Verify",
		 * "fc22363129b603abc6401493d10ab48fc75257b994829708220e946c54c1a7a5###1");
		 * 
		 * httpConn.setRequestProperty("Content-Type", "application/json");
		 * 
		 * 
		 * httpConn.setDoOutput(true);
		 * 
		 * OutputStreamWriter writer = new
		 * OutputStreamWriter(httpConn.getOutputStream()); writer.write(
		 * "\"merchantId\": \"PGTESTPAYUAT86\",\n\t\t\t\t\"merchantTransactionId\": \"MT7850590068188104\",\n\t\t\t\t\"merchantUserId\": \"MUID123\",\n\t\t\t\t\"amount\": 100,\n\t\t\t\t\"redirectUrl\": \"https://webhook.site/redirect-url\",\n\t\t\t\t\"redirectMode\": \"REDIRECT\",\n\t\t\t\t\"callbackUrl\": \"https://webhook.site/callback-url\",\n\t\t\t\t\"mobileNumber\": \"9999999999\",\n\t\t\t\t\"paymentInstrument\": {\n\t\t\t\t\t\"type\": \"PAY_PAGE\"\n\t\t\t\t}"
		 * ); writer.flush(); writer.close();
		 * 
		 * httpConn.getOutputStream().close();
		 * 
		 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
		 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
		 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
		 * s.next() : ""; System.out.println(response);
		 */

		/*
		 * URL url = new
		 * URL("https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay");
		 * HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		 * httpConn.setRequestMethod("POST");
		 * httpConn.setRequestProperty("Content-type", "application/json");
		 * httpConn.setRequestProperty("X-Verify",
		 * "2033cf2152d3983077baf662c8ed095dd81fae3489685c2a4e4776a1d7b18ce9###1");
		 * 
		 * httpConn.setDoOutput(true); OutputStreamWriter writer = new
		 * OutputStreamWriter(httpConn.getOutputStream()); writer.
		 * write("{\n\"request\" :\"ewoibWVyY2hhbnRJZCI6ICJQR1RFU1RQQVlVQVQ4NiIsCiAgIm1lcmNoYW50VHJhbnNhY3Rpb25JZCI6ICJNVDc4NTA1OTAwNjgxODgxMDQiLAogICJtZXJjaGFudFVzZXJJZCI6ICJNVUlEMTIzIiwKICAiYW1vdW50IjogMTAwLAogICJyZWRpcmVjdFVybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9yZWRpcmVjdC11cmwiLAogICJyZWRpcmVjdE1vZGUiOiAiUkVESVJFQ1QiLAogICJjYWxsYmFja1VybCI6ICJodHRwczovL3dlYmhvb2suc2l0ZS9jYWxsYmFjay11cmwiLAogICJtb2JpbGVOdW1iZXIiOiAiOTk5OTk5OTk5OSIsCiAgInBheW1lbnRJbnN0cnVtZW50IjogewogICAgInR5cGUiOiAiUEFZX1BBR0UiCiAgfQp9\"\n}"
		 * ); writer.flush(); writer.close(); httpConn.getOutputStream().close();
		 * 
		 * InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ?
		 * httpConn.getInputStream() : httpConn.getErrorStream(); Scanner s = new
		 * Scanner(responseStream).useDelimiter("\\A"); String response = s.hasNext() ?
		 * s.next() : ""; System.out.println(response);
		 * 
		 * 
		 * String merchantId = "PGTESTPAYUAT86"; String saltKey =
		 * "96434309-7796-489d-8924-ab56988a6076"; Integer saltIndex = 1; Env env =
		 * Env.UAT; boolean shouldPublishEvents = true; PhonePePaymentClient
		 * phonepeClient = new PhonePePaymentClient(merchantId, saltKey, saltIndex, env,
		 * shouldPublishEvents); String merchantTransactionId =
		 * UUID.randomUUID().toString().substring(0, 34); long amount = 100; String
		 * callbackurl = "https://www.merchant.com/callback"; String merchantUserId =
		 * "merchantUserId"; String redirectUrl = "https://www.merchant.com/redirect";
		 * String redirectMode = "REDIRECT";
		 * 
		 * PgPayRequest pgPayRequest =
		 * PgPayRequest.PayPagePayRequestBuilder().amount(amount).merchantId(merchantId)
		 * 
		 * .merchantTransactionId(merchantTransactionId).callbackUrl(callbackurl).
		 * merchantUserId(merchantUserId)
		 * 
		 * .redirectUrl(redirectUrl).redirectMode(redirectMode).build();
		 * 
		 * PhonePeResponse<PgPayResponse> payResponse = phonepeClient.pay(pgPayRequest);
		 * PayPageInstrumentResponse payPageInstrumentResponse =
		 * (PayPageInstrumentResponse) payResponse.getData() .getInstrumentResponse();
		 * String url = payPageInstrumentResponse.getRedirectInfo().getUrl();
		 * System.out.println(payResponse);
		 */
		String merchantId = "PGTESTPAYUAT86";
		String saltKey = "96434309-7796-489d-8924-ab56988a6076";
		Integer saltIndex = 1;
		Env env = Env.UAT;
		boolean shouldPublishEvents = true;
		PhonePePaymentClient phonepeClient = new PhonePePaymentClient(merchantId, saltKey, saltIndex, env,
				shouldPublishEvents);
		String merchantTransactionId = UUID.randomUUID().toString().substring(0, 34);
		long amount = 100;
		/* String callbackurl = "https://www.merchant.com/callback"; */
		String callbackurl = "http://localhost:8000";
		String merchantUserId = "merchantUserId";
		/* String redirectUrl = "https://www.merchant.com/redirect"; */
		String redirectUrl = "http://localhost:8000";
		String redirectMode = "REDIRECT";

		PgPayRequest pgPayRequest = PgPayRequest.PayPagePayRequestBuilder().amount(amount).merchantId(merchantId)

				.merchantTransactionId(merchantTransactionId).callbackUrl(callbackurl).merchantUserId(merchantUserId)

				.redirectUrl(redirectUrl).redirectMode(redirectMode).build();

		PhonePeResponse<PgPayResponse> payResponse = phonepeClient.pay(pgPayRequest);
		PayPageInstrumentResponse payPageInstrumentResponse = (PayPageInstrumentResponse) payResponse.getData()
				.getInstrumentResponse();
		String url = payPageInstrumentResponse.getRedirectInfo().getUrl();
		System.out.println(payResponse);
		model.addAttribute("phonepe_checkout", url);

		/*
		 * HttpClient httpClient = HttpClientBuilder.create().build(); try { HttpPost
		 * request = new
		 * HttpPost("https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay");
		 * StringEntity params = new StringEntity("{\"request\":\"xyz\"} ");
		 * request.addHeader("content-type", "application/json");
		 * request.addHeader("x-verify",
		 * "fc22363129b603abc6401493d10ab48fc75257b994829708220e946c54c1a7a5###1");
		 * 
		 * //request.setEntity(params); HttpResponse response =
		 * httpClient.execute(request); System.out.println(response); } catch (Exception
		 * ex) { } finally { // @Deprecated
		 * httpClient.getConnectionManager().shutdown(); }
		 */

		return "index";
	}

	@GetMapping("/phonepe-test")
	public String phonepeTest() {
		try {

			URL url = new URL("https://apps-uat.phonepe.com/v3/transaction/sdk-less/initiate");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-type", "application/json");
			httpConn.setRequestProperty("X-Verify",
					"46efadea4c61586d23d81712f95a26d43724965197a621bdd1e31cfdbac69ff8###1");
			httpConn.setRequestProperty("X-CLIENT-ID", "PGTESTPAYUAT86");
			httpConn.setRequestProperty("X-CALLBACK-URL", "https://localhost:8000/phonepe-payment");
			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(
					"{\n\"request\" :\"ewoibWVyY2hhbnRJZCI6ICJQR1RFU1RQQVlVQVQ4NiIsCiAiYW1vdW50IjogMzkwMCwKInZhbGlkRm9yIjo5MDAwMDAsCiJ0cmFuc2FjdGlvbklkIjoiVDEyUlRSVEJKR0dUR0pOWUZUIiwKIm1lcmNoYW50T3JkZXJJZCI6IlQxMlJUUlRCSkdHVEdKTllGVCIsCiAicmVkaXJlY3RVcmwiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAvcGhvbmVwZS10ZXN0IiwKICJ0cmFuc2FjdGlvbkNvbnRleHQiOiAiZXdvZ0lDSnRaWEpqYUdGdWRFbGtJam9pVUVkVVJWTlVVRUZaVlVGVU9EWWlMQW9nSUNKaGJXOTFiblFpT2pNNU1EQXNDaUFnSW5aaGJHbGtSbTl5SWpvZ09UQXdNREFzQ2lBZ0luUnlZVzV6WVdOMGFXOXVTV1FpT2lBaU5XTXdabUpoTmpJMk5qSTNaamd6WVdZMk9DSXNDaUFnSW0xbGNtTm9ZVzUwVDNKa1pYSkpaQ0k2SWxOSlJDSXNDaUFnSW5SeVlXNXpZV04wYVc5dVZIbHdaU0k2SUNKVFZFRk9SRUZTUkNJc0NpQWdJblJ5WVc1ellXTjBhVzl1UTI5dWRHVjRkQ0k2SUhzS0lDQWdJQ0p2Y21SbGNrTnZiblJsZUhRaU9pQjdDaUFnSUNBZ0lDSjBjbUZqYTJsdVowbHVabThpT2lCN0NpQWdJQ0FnSUNBZ0luUjVjR1VpT2lBaVNGUlVVRk1pTEFvZ0lDQWdJQ0FnSUNKMWNtd2lPaUFpYUhSMGNITTZMeTluYjI5bmJHVXVZMjl0SWdvZ0lDQWdJQ0I5Q2lBZ0lDQjlMQW9nSUNBZ0ltWmhjbVZFWlhSaGFXeHpJam9nZXdvZ0lDQWdJQ0FpZEc5MFlXeEJiVzkxYm5RaU9pQXpPVEF3TEFvZ0lDQWdJQ0FpY0dGNVlXSnNaVUZ0YjNWdWRDSTZJRE01TURBS0lDQWdJSDBzQ2lBZ0lDQWlZMkZ5ZEVSbGRHRnBiSE1pT2lCN0NpQWdJQ0FnSUNKallYSjBTWFJsYlhNaU9pQmJDaUFnSUNBZ0lDQWdld29nSUNBZ0lDQWdJQ0FnSW1OaGRHVm5iM0o1SWpvZ0lsTklUMUJRU1U1SElpd0tJQ0FnSUNBZ0lDQWdJQ0pwZEdWdFNXUWlPaUFpTVRJek5EVTJOemd3T1NJc0NpQWdJQ0FnSUNBZ0lDQWljSEpwWTJVaU9pQXpPVEF3TEFvZ0lDQWdJQ0FnSUNBZ0ltbDBaVzFPWVcxbElqb2dJbFJGVTFRaUxBb2dJQ0FnSUNBZ0lDQWdJbkYxWVc1MGFYUjVJam9nTVN3S0lDQWdJQ0FnSUNBZ0lDSmhaR1J5WlhOeklqb2dld29nSUNBZ0lDQWdJQ0FnSUNBaVlXUmtjbVZ6YzFOMGNtbHVaeUk2SUNKVVJWTlVJaXdLSUNBZ0lDQWdJQ0FnSUNBZ0ltTnBkSGtpT2lBaVZFVlRWQ0lzQ2lBZ0lDQWdJQ0FnSUNBZ0lDSndhVzVqYjJSbElqb2dJbFJGVTFRaUxBb2dJQ0FnSUNBZ0lDQWdJQ0FpWTI5MWJuUnllU0k2SUNKVVJWTlVJaXdLSUNBZ0lDQWdJQ0FnSUNBZ0lteGhkR2wwZFdSbElqb2dNU3dLSUNBZ0lDQWdJQ0FnSUNBZ0lteHZibWRwZEhWa1pTSTZJREVLSUNBZ0lDQWdJQ0FnSUgwc0NpQWdJQ0FnSUNBZ0lDQWljMmhwY0hCcGJtZEpibVp2SWpvZ2V3b2dJQ0FnSUNBZ0lDQWdJQ0FpWkdWc2FYWmxjbmxVZVhCbElqb2dJbE5VUVU1RVFWSkVJaXdLSUNBZ0lDQWdJQ0FnSUNBZ0luUnBiV1VpT2lCN0NpQWdJQ0FnSUNBZ0lDQWdJQ0FnSW5ScGJXVnpkR0Z0Y0NJNklERTJOakUxTkRBeU1qQXNDaUFnSUNBZ0lDQWdJQ0FnSUNBZ0lucHZibVZQWm1aVFpYUWlPaUFpS3pBMU9qTXdJZ29nSUNBZ0lDQWdJQ0FnSUNCOUNpQWdJQ0FnSUNBZ0lDQjlDaUFnSUNBZ0lDQWdmUW9nSUNBZ0lDQmRDaUFnSUNCOUNpQWdmUXA5Q2c9PSIKfQ==\"\n}");
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
		return "index";
	}

	@GetMapping("/phonepe-submerchant")
	public String phonepeSubMerchant() {
		try {

			URL url = new URL("https://api-preprod.phonepe.com/apis/pg-sandbox/v1/submerchant");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-type", "application/json");
			httpConn.setRequestProperty("X-Verify",
					"eae0d43394cad1fdcba592f6405880ecb71fe54a54b333f757dcc4bf1ee3d194###1");
			/*
			 * httpConn.setRequestProperty("X-CLIENT-ID", "PGTESTPAYUAT86");
			 * httpConn.setRequestProperty("X-CALLBACK-URL",
			 * "https://localhost:8000/phonepe-payment");
			 */
			httpConn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(
					"{\n\"request\" :\"ewogICJtZXJjaGFudElkIjogIlBHVEVTVFBBWVVBVDg2IiwKICAiZnVsbE5hbWUiOiAiQ0JzZGMiLAogICJkaXNwbGF5TmFtZSI6ICJNeXVqIiwKICAibWNjIjogIjhYWFgiLAogICJleHRlcm5hbE1lcmNoYW50VHlwZSI6ICJPTkxJTkUiLAogICJleHRlcm5hbE9uYm9hcmRpbmdUeXBlIjogIkFHR1JFR0FUT1JTIiwKICAiYWdncmVnYXRvclN1Yk1lcmNoYW50SWQiOiAiMTJkNDIyM2MtNzcxMy00Njc2LWIyNDItZWNjNTdlZDMyOWIyIgp9Cg==\"\n}");
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
		return "index";
	}

	@GetMapping("payu-test")
	public String payuTest() {
		/*
		 * HttpRequest request = HttpRequest.newBuilder()
		 * .uri(URI.create("https://test.payu.in/_payment")) .header("accept",
		 * "text/plain") .header("content-type", "application/x-www-form-urlencoded")
		 * .method("POST", HttpRequest.BodyPublishers.ofString(
		 * "key=JPM7Fg&surl=https%3A%2F%2Ftest-payment-middleware.payu.in%2FsimulatorResponse&furl=https%3A%2F%2Ftest-payment-middleware.payu.in%2FsimulatorResponse&txnid=ypl938459435&amount=100&productinfo=iPhone&firstname=Kumar&email=budhakumar21%40gmail.com&phone=8527508751&address1=A2B%2F184A%2CEkta%20Appartments&address2=Paschim%20Vihar&city=New%20Delhi&state=Delhi&country=India&zipcode=110063&hash=7983405b750a0d855535bcddc516ff4f6597a9730b8e46b2cc3a5123444ce2e867271267e668381414bebe1d418da7b429c6803a7ef3144b972e8273f078f76b"
		 * )) .build(); HttpResponse<String> response; try { response =
		 * HttpClient.newHttpClient().send(request,
		 * HttpResponse.BodyHandlers.ofString()); System.out.println(response.body()); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			URL url = new URL("https://test.payu.in/_payment");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(
					"key=obanPv&txnid=txnid63936884160&amount=10.00&firstname=PayU User&email=test@gmail.com&phone=9876543210&productinfo=iPhone&surl=https://test-payment-middleware.payu.in/simulatorResponse&furl=https://test-payment-middleware.payu.in/simulatorResponse&hash=58e1159c1eac24c4b9bb66a5381100dffde5195f187460e5390115c748e2a2262fb6ba473c75c7554a1740a59b266f0e1074d7e7b9ef31910dbca7fb31a0975c");
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
		return "index";

	}

	@GetMapping("/mobile-otpp")
	public String sendOtp() {
		try {
			/*
			 * com.mashape.unirest.http.HttpResponse<String> response =
			 * Unirest.post("https://www.fast2sms.com/dev/bulkV2") .header("authorization",
			 * "tlSRwnXvvicdGrlFCsnWG0sE6gn2AOIF8Z9hZEKuzLakuYdZX3yP19ctDwul")
			 * .header("Content-Type", "application/x-www-form-urlencoded")
			 * .body("variables_values=85430&route=otp&numbers=8527508751").asString();
			 */
			URL url = new URL("https://www.fast2sms.com/dev/bulkV2");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			httpConn.setRequestProperty("authorization",
					"tlSRwnXvvicdGrlFCsnWG0sE6gn2AOIF8Z9hZEKuzLakuYdZX3yP19ctDwul");
			httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write("variables_values=5599&route=otp&numbers=9205080980");
			writer.flush();
			writer.close();
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
					: httpConn.getErrorStream();
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			System.out.println(response);
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	@GetMapping("/terms-conditions")
	public String termsAndConditions() {
		return "terms_conditions";
	}

	@GetMapping("/easyquery-login")
	public String easyQueryLogin(Model model) {
		model.addAttribute("seller", new Seller());
		return "easyquery_login";
	}
	/*
	 * @GetMapping("/date-time") public String dateTime() { DateTimeFormatter df =
	 * DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); LocalDateTime now =
	 * LocalDateTime.now(); System.out.println(df.format(now)); return
	 * "customer_index"; }
	 */
	/*
	 * @GetMapping("/display") public String display(Model model) { Seller seller =
	 * new Seller(); model.addAttribute("seller", seller); return "display"; }
	 */

}
