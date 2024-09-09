package com.shopping_centre.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping_centre.dao.CustomerItemRepository;
import com.shopping_centre.dao.CustomerRepository;
import com.shopping_centre.dao.ProductRepository;

import com.shopping_centre.entities.Customer;
import com.shopping_centre.entities.CustomerItem;
import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;





@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ProductRepository productRepository;
	/*
	 * @Autowired private ServiceRepository serviceRepository;
	 */
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerItemRepository customerItemRepository;

	@GetMapping("/view_product")
	// @Transactional
	public String viewProduct(@RequestParam long id, @RequestParam String page, @RequestParam Optional<String> c_email,
			Model model) {
		System.out.println("id : "+id);
		//System.out.println(productRepository.findById(Long.parseLong(id)));
		Product product = productRepository.findById(id).get();
		System.out.println(c_email);
		model.addAttribute("product", product);
		if (c_email.isPresent()) {
			Customer customer = customerRepository.findByEmail(c_email.get());
			model.addAttribute("c_email", c_email.get());
			String greetName;
			try {
				greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = customer.getName();
			}
			model.addAttribute("greeting", greetName);
			List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
					"added_to_cart");
			List<Product> products = new ArrayList<>();
			double totalPrice1 = 0;
			for (CustomerItem ci : customerItems) {
				Product p = productRepository.findById(ci.getProductId()).get();
				/* p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/"))); */
				/*
				 * if (p.getPrice().contains("/")) { String unit =
				 * p.getPrice().substring(p.getPrice().indexOf("/") + 1);
				 * p.setTempPrice(Double.parseDouble(p.getPrice().substring(0,
				 * p.getPrice().indexOf("/")))); p.setUnit(unit); } else {
				 * p.setTempPrice1(Integer.parseInt(p.getPrice()));
				 * p.setTePrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				 * //totalPrice1 += p.getTempPrice1();
				 * 
				 * p.setUnit(""); }
				 */
				if (p.getPrice().contains("/")) {
					String unit = p.getPrice().substring(p.getPrice().indexOf("/") + 1);
					System.out.println("unit :" + unit);
					if (ci.getQty() != null) {

						p.setQty(ci.getQty());
						p.setTempPrice(ci.getPrice());
						System.out.println("hello :" + p.getPrice());
						totalPrice1 += p.getTempPrice();
					} else {
						p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
						totalPrice1 += p.getTempPrice();
						// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
					}

					p.setUnit(unit);
				} else {
					if (ci.getQty2() != null) {

						p.setQty2(ci.getQty2());
						p.setTempPrice1(ci.getPrice1());
						System.out.println("hello :" + p.getPrice());
						totalPrice1 += p.getTempPrice1();
					} else {
						p.setTempPrice1(Integer.parseInt(p.getPrice()));
						totalPrice1 += p.getTempPrice1();
						// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
					}
					p.setUnit("");
				}
				products.add(p);
				/*
				 * double productPrice = Double.parseDouble(p.getPrice()); totalPrice1 +=
				 * productPrice;
				 */
				System.out.println("list :" + products);
			}
			model.addAttribute("totalPrice1", totalPrice1);
			model.addAttribute("products", products);
			if (products != null) {
				model.addAttribute("noOfItems", products.size());
			}

			if (products.isEmpty()) {
				model.addAttribute("products", null);
			}

			if (customerItems.isEmpty()) {
				model.addAttribute("customerItems", null);
			}
			CustomerItem customerItem = customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
					product.getP_id());
			System.out.println("customer item :" + customerItem);
			if (customerItem == null) {
				customerItem = new CustomerItem();
				/*
				 * customerItem.setStatus("adding"); customerItemRepository.save(customerItem);
				 * CustomerItem customerItem1 = customerItemRepository.findByStatus("adding");
				 * System.out.println("id :"+customerItem1.getId());
				 */

				// Product p = em.merge(product);
				customerItem.setProductId(product.getP_id());
				customerItem.setCustomerId(customer.getId());

				customerItem.setStatus("viewed");
				// customerItem.setStatus("viewed");

				/*
				 * product.getCustomerItem().add(customerItem);
				 * customer.getCustomerItem().add(customerItem);
				 */
				/* Seller seller = sellerRepository.findByEmail(c_email.get()); */
				// seller.getProducts().clear();
				/*
				 * product.setSeller(seller); seller.getProducts().add(product);
				 * sellerRepository.save(seller);
				 */
				/*
				 * productRepository.save(product); customerRepository.save(customer);
				 */
				// customerRepository.save(customer);
				customerItemRepository.save(customerItem);
			} else {
				if (customerItem.getStatus().equals("added_to_cart")) {
					model.addAttribute("product_added", "yes");
				}
			}
		}
		if (page.equals("customer_index")) {

			model.addAttribute("allowed", "yes");
		} else {
			model.addAttribute("allowed", "no");
		}
		model.addAttribute("manage_cart","show");
		return "display";
	}

	/*
	 * @GetMapping("/view_service") public String viewService(@RequestParam long id,
	 * Model model) { System.out.println("hello"); Service service =
	 * serviceRepository.findById(id).get(); model.addAttribute("service", service);
	 * return "display"; }
	 */

	@GetMapping("/show_cat_items")
	public String showCatItems(@RequestParam String cat_name, @RequestParam String page,
			@RequestParam Optional<String> c_email, Model model) {
		List<Product> products = productRepository.findByCategory(cat_name);
		//List<Service> services = serviceRepository.findByCategory(cat_name);
		model.addAttribute("p_list1", products);
		//model.addAttribute("s_list1", services);
		model.addAttribute("category", cat_name);
		model.addAttribute("seller", new Seller());
		model.addAttribute("show", "show");
		if (!(c_email.isEmpty())) {
			System.out.println("mail233 :" + c_email.get());
			Customer customer = customerRepository.findByEmail(c_email.get());
			model.addAttribute("c_email", customer.getEmail());
			String greetName;
			try {
				greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = customer.getName();
			}
			model.addAttribute("greeting", greetName);
			// model.addAttribute("c_email",c_email.get());
		}
		if (products != null) {
			if (products.isEmpty()) {
				model.addAttribute("p_list1", null);
			}
		}
		/*
		 * if (services != null) { if (services.isEmpty()) {
		 * model.addAttribute("s_list1", null); } }
		 */
		System.out.println("page :" + page);
		if (page.equals("index"))
			return "index";
		else
			return "customer_index";

	}

	@GetMapping("/show_product")
	public String showProduct(@RequestParam long id, @RequestParam String page, @RequestParam Optional<String> c_email,
			Model model) {
		Product product = productRepository.findById(id).get();
		model.addAttribute("product", product);
		model.addAttribute("seller", new Seller());
		model.addAttribute("show", "show");
		model.addAttribute("category", null);
		if (!(c_email.isEmpty())) {
			model.addAttribute("c_email", c_email.get());
			Customer customer = customerRepository.findByEmail(c_email.get());
			String greetName;
			try {
				greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
			} catch (StringIndexOutOfBoundsException se) {
				greetName = customer.getName();
			}
			model.addAttribute("greeting", greetName);
		}
		if (page.equals("index"))
			return "index";
		else
			return "customer_index";
	}

	/*
	 * @GetMapping("/show_service") public String showService(@RequestParam long
	 * id, @RequestParam String page, @RequestParam Optional<String> c_email, Model
	 * model) { System.out.println("shows0"); //Service service =
	 * serviceRepository.findById(id).get(); model.addAttribute("service", service);
	 * model.addAttribute("seller", new Seller()); model.addAttribute("show",
	 * "show"); model.addAttribute("category", null); if (!(c_email.isEmpty())) {
	 * model.addAttribute("c_email", c_email.get()); Customer customer =
	 * customerRepository.findByEmail(c_email.get()); String greetName; try {
	 * greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
	 * } catch (StringIndexOutOfBoundsException se) { greetName =
	 * customer.getName(); } model.addAttribute("greeting", greetName); } if
	 * (page.equals("index")) return "index";
	 * 
	 * else return "customer_index"; }
	 */
	@GetMapping("/add_to_cart")
	public String addToCart(@RequestParam String c_email, @RequestParam long p_id, Model model) {
		System.out.println("mail " + c_email);
		Customer customer = customerRepository.findByEmail(c_email);
		Product product = productRepository.findById(p_id).get();
		System.out.println("pid : " + product.getP_id());
		System.out.println("customer" + customer.getName());
		CustomerItem customerItem = customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
				product.getP_id());
		customerItem.setStatus("added_to_cart");
		customerItemRepository.save(customerItem);
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		List<Product> products = new ArrayList<>();
		double totalPrice1 = 0;
		for (CustomerItem ci : customerItems) {
			Product p = productRepository.findById(ci.getProductId()).get();
			String price = p.getPrice();
			// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
			/*
			 * if (p.getPrice().contains("/")) { String unit =
			 * p.getPrice().substring(p.getPrice().indexOf("/") + 1);
			 * p.setTempPrice(Double.parseDouble(p.getPrice().substring(0,
			 * p.getPrice().indexOf("/")))); p.setTePrice(p.getPrice().substring(0,
			 * p.getPrice().indexOf("/"))); totalPrice1 += p.getTempPrice(); p.setUnit(unit);
			 * } else {
			 * 
			 * p.setTempPrice1(Integer.parseInt(p.getPrice()));
			 * p.setTePrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
			 * totalPrice1 += p.getTempPrice1(); p.setUnit(""); }
			 */
			if (p.getPrice().contains("/")) {
				String unit = p.getPrice().substring(p.getPrice().indexOf("/") + 1);
				System.out.println("unit :" + unit);
				if (ci.getQty() != null) {

					p.setQty(ci.getQty());
					p.setTempPrice(ci.getPrice());
					System.out.println("hello :" + p.getPrice());
					totalPrice1 += p.getTempPrice();
				} else {
					p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
					totalPrice1 += p.getTempPrice();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}

				p.setUnit(unit);
			} else {
				if (ci.getQty2() != null) {

					p.setQty2(ci.getQty2());
					p.setTempPrice1(ci.getPrice1());
					System.out.println("hello :" + p.getPrice());
					totalPrice1 += p.getTempPrice1();
				} else {
					p.setTempPrice1(Integer.parseInt(p.getPrice()));
					totalPrice1 += p.getTempPrice1();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}
				p.setUnit("");
			}
			products.add(p);
			// p.setPrice(price);
			System.out.println("list :" + products);
		}
		int totalPrice = (int) Math.round(totalPrice1);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("products", products);
		String greetName;
		try {
			greetName = customer.getName().substring(0, customer.getName().indexOf(" "));
		} catch (StringIndexOutOfBoundsException se) {
			greetName = customer.getName();
		}
		model.addAttribute("greeting", greetName);
		// model.addAttribute("allowed", "yes");
		model.addAttribute("product_added", "yes");
		model.addAttribute("product", product);
		if (products != null) {
			model.addAttribute("noOfItems", products.size());
		}

		if (products.isEmpty()) {
			model.addAttribute("products", null);
		}
		model.addAttribute("c_email", c_email);
		return "display";
	}

	@GetMapping("/update_cart")
	public String updateCart(@RequestParam String c_email, @RequestParam long p_id, @RequestParam long product_id,
			@RequestParam String qty, Model model) {
		System.out.println("mail " + c_email);
		Customer customer = customerRepository.findByEmail(c_email);
		Product product = productRepository.findById(p_id).get();
		Product product1 = productRepository.findById(product_id).get();
		System.out.println("pid : " + product.getP_id());
		System.out.println("customer" + customer.getName());
		double qty1 = 0;
		int qty2 = 0;
		if (product.getPrice().contains("/")) {
			qty1 = Double.parseDouble(qty);
			product.setTempPrice(
					Double.parseDouble(product.getPrice().substring(0, product.getPrice().indexOf("/"))) * qty1);
		} else {
			qty2 = Integer.parseInt(qty);
			product.setTempPrice1(Integer.parseInt(product.getPrice()) * qty2);
		}
		CustomerItem customerItem = customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
				product.getP_id());
		if (qty2 == 0) {
			customerItem.setQty(qty1);
			customerItem.setPrice(product.getTempPrice());
		} else {
			customerItem.setQty2(qty2);
			customerItem.setPrice1(product.getTempPrice1());
		}
		customerItemRepository.save(customerItem);
		/*
		 * CustomerItem customerItem =
		 * customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
		 * product.getP_id()); customerItem.setStatus("added_to_cart");
		 * customerItemRepository.delete(customerItem);
		 */
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		List<Product> products = new ArrayList<>();
		double totalPrice1 = 0;
		// int totalPrice11 = 0;
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
					totalPrice1 += p.getTempPrice();
				} else {
					p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
					totalPrice1 += p.getTempPrice();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}

				p.setUnit(unit);
			} else {
				if (ci.getQty2() != null) {

					p.setQty2(ci.getQty2());
					p.setTempPrice1(ci.getPrice1());
					System.out.println("hello :" + p.getPrice());
					totalPrice1 += p.getTempPrice1();
				} else {
					p.setTempPrice1(Integer.parseInt(p.getPrice()));
					totalPrice1 += p.getTempPrice1();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}
				p.setUnit("");
			}
			// }
			// String price = p.getPrice();
			// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
			/* totalPrice1+=p.getTempPrice(); */

			/*
			 * if (p.getTempPrice() != null) { totalPrice1 += p.getTempPrice();
			 * p.setTempPrice(null); } else { totalPrice1 +=
			 * Double.parseDouble(p.getPrice()); }
			 * 
			 * if (p.getTempPrice1() != null) { totalPrice1 += p.getTempPrice1();
			 * p.setTempPrice1(null); } else { totalPrice1 += Integer.parseInt(p.getPrice());
			 * }
			 */

			/*
			 * if (p.getTempPrice1() != null) { totalPrice1 += p.getTempPrice1(); } else {
			 * totalPrice1 += Integer.parseInt(p.getPrice()); }
			 */
			products.add(p);
			// p.setPrice(price);
			System.out.println("list :" + products);
		}
		int totalPrice = (int) Math.round(totalPrice1);
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
		model.addAttribute("product", product1);
		if (products != null) {
			model.addAttribute("noOfItems", products.size());
		}

		if (products.isEmpty()) {
			model.addAttribute("products", null);
		}
		System.out.println("mail23" + c_email);
		model.addAttribute("c_email", c_email);
		return "display";
	}

	@PostMapping("/remove_from_cart")
	public String removeFromCart(@RequestParam String c_email, @RequestParam long p_id, @RequestParam long product_id,
			Model model) {
		System.out.println("mail12 " + c_email);
		// System.out.println("id12 " + p_id);
		Customer customer = customerRepository.findByEmail(c_email);
		Product product = productRepository.findById(p_id).get();
		Product product1 = productRepository.findById(product_id).get();
		System.out.println("pid : " + product.getP_id());
		System.out.println("customer" + customer.getName());
		/*
		 * double qty1 = 0; int qty2 = 0;
		 */
		/*
		 * if (product.getPrice().contains("/")) { qty1 = Double.parseDouble(qty);
		 * product.setTempPrice( Double.parseDouble(product.getPrice().substring(0,
		 * product.getPrice().indexOf("/"))) * qty1); } else { qty2 =
		 * Integer.parseInt(qty);
		 * product.setTempPrice1(Integer.parseInt(product.getPrice()) * qty2); }
		 */
		CustomerItem customerItem = customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
				product.getP_id());
		customerItem.setStatus("viewed");
		customerItem.setPrice(null);
		customerItem.setPrice1(null);
		customerItem.setQty(null);
		customerItem.setQty2(null);
		customerItemRepository.save(customerItem);
		/*
		 * if (qty2 == 0) { customerItem.setQty(qty1);
		 * customerItem.setPrice(product.getTempPrice()); } else {
		 * customerItem.setQty2(qty2); customerItem.setPrice1(product.getTempPrice1());
		 * } customerItemRepository.save(customerItem);
		 */
		/*
		 * CustomerItem customerItem =
		 * customerItemRepository.findByCustomerIdAndProductId(customer.getId(),
		 * product.getP_id()); customerItem.setStatus("added_to_cart");
		 * customerItemRepository.delete(customerItem);
		 */
		List<CustomerItem> customerItems = customerItemRepository.findByCustomerIdAndStatus(customer.getId(),
				"added_to_cart");
		List<Product> products = new ArrayList<>();

		double totalPrice1 = 0;
		// int totalPrice11 = 0;
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
					totalPrice1 += p.getTempPrice();
				} else {
					p.setTempPrice(Double.parseDouble(p.getPrice().substring(0, p.getPrice().indexOf("/"))));
					totalPrice1 += p.getTempPrice();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}

				p.setUnit(unit);
			} else {
				if (ci.getQty2() != null) {

					p.setQty2(ci.getQty2());
					p.setTempPrice1(ci.getPrice1());
					System.out.println("hello :" + p.getPrice());
					totalPrice1 += p.getTempPrice1();
				} else {
					p.setTempPrice1(Integer.parseInt(p.getPrice()));
					totalPrice1 += p.getTempPrice1();
					// p.setPrice(p.getPrice().substring(0, p.getPrice().indexOf("/")));
				}
				p.setUnit("");
			}
			// }
			// String price = p.getPrice();
			// p.setPrice(p.getPrice().substring(p.getPrice().indexOf("/")));
			/* totalPrice1+=p.getTempPrice(); */

			/*
			 * if (p.getTempPrice() != null) { totalPrice1 += p.getTempPrice();
			 * p.setTempPrice(null); } else { totalPrice1 +=
			 * Double.parseDouble(p.getPrice()); }
			 * 
			 * if (p.getTempPrice1() != null) { totalPrice1 += p.getTempPrice1();
			 * p.setTempPrice1(null); } else { totalPrice1 += Integer.parseInt(p.getPrice());
			 * }
			 */

			/*
			 * if (p.getTempPrice1() != null) { totalPrice1 += p.getTempPrice1(); } else {
			 * totalPrice1 += Integer.parseInt(p.getPrice()); }
			 */
			products.add(p);
			// p.setPrice(price);
			System.out.println("list :" + products);
		}
		int totalPrice = (int) Math.round(totalPrice1);
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
		model.addAttribute("product", product1);
		if (products != null) {
			model.addAttribute("noOfItems", products.size());
		}

		if (products.isEmpty()) {
			model.addAttribute("products", null);
		}
		model.addAttribute("c_email", c_email);
		System.out.println(product_id + ":" + p_id);
		// model.addAttribute("removed", "removed");
		if (product_id == p_id) {
			System.out.println("matched");
			model.addAttribute("removed", "removed");
		}

		return "display";
	}
	@GetMapping("/search/{query}")
	public ResponseEntity<List<Product>> search(@PathVariable String query){
		List<Product> products = productRepository.findByName(query);
		System.out.println("products : "+products);
		return ResponseEntity.ok(products);
	}

}
