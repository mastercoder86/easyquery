package com.shopping_centre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping_centre.dao.PasscodeRepository;
import com.shopping_centre.entities.Passcode;
import com.shopping_centre.entities.Product;
import com.shopping_centre.entities.Seller;

@Controller
@RequestMapping("/easyquery")
public class EasyQueryController {
	@Autowired
	private PasscodeRepository passcodeRepository;

	/*
	 * @ModelAttribute public void commonData(Model model) {
	 * model.addAttribute("seller", new Seller()); }
	 */

	@PostMapping("/do-login")
	public String doLogin(@RequestParam String username, @RequestParam String password, Model model) {
		if (username.equals("easyquery") && password.equals("easy_query"))
			model.addAttribute("credentials7", "good");
		else
			model.addAttribute("credentials7", "bad");

		return "easyquery_login";
	}

	@PostMapping("/submit-passcode")
	public String submitPasscode(@RequestParam String passcode, Model model) {
		Passcode passcode1 = new Passcode();
		passcode1.setPasscode(passcode);
		passcodeRepository.save(passcode1);
		model.addAttribute("credentials6", "good");
		return "easyquery_login";
	}

	/*
	 * @GetMapping("/check-passcode") public String checkPasscode(Model model) {
	 * Passcode passcode = passcodeRepository.findFirstByOrderByIdDesc();
	 * System.out.println("pass:" + passcode); if (passcode != null)
	 * model.addAttribute("passcode", passcode.getPasscode()); else
	 * model.addAttribute("passcode", null); return "easyquery_login"; }
	 */
	@GetMapping("/check-passcode")
	public ResponseEntity<String> checkPasscode() {
		Passcode passcode = passcodeRepository.findFirstByOrderByIdDesc();
		System.out.println("pass:" + passcode);
		if (passcode != null)
			return ResponseEntity.ok(passcode.getPasscode());
		else
			return ResponseEntity.ok("no passcode");

	}

}
