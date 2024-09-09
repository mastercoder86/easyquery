const show_search = (el) => {
	var searchArea = document.getElementsByClassName("search_area");
	var s1 = document.getElementsByClassName("s1");
	var shopSection = document.getElementsByClassName("shop_section");
	if (shopSection.length > 0) {
		for (let i = 0; i < shopSection.length; i++) {
			for (let j = 0; j < shopSection[i].getElementsByTagName("div").length; j++) {
				shopSection[i].getElementsByTagName("div")[j].style.zIndex = "0";
			}
		}
	}
	console.log(searchArea.length);
	if (searchArea.length > 0 && s1.length > 0) {

		var found = [];
		var element = el;
		for (let i = 0; i < searchArea.length; i++) {
			if (s1[i] === element) {
				//window.location.href="/login-form";
				console.log("hello");
				searchArea[i].style.display = "block";
				/*if (searchArea[i].innerHTML.includes("Search Your")) {
					searchArea[i].querySelector("h1").style.marginLeft = "10%";
				}*/
				found.push(i);
			}
			/*if (s1[i].innerHTML === "Seller") {
				swal({
					title: "Oops!",
					text: "Please fill the fields correctly",
					
					icon: "error",
					button: "Try Again!",
				});
			}*/
			/*if (searchArea[i].innerHTML.includes("Seller Login")) {
				searchArea[i].style.display = "block";
				found.push(i);
				console.log("seller");
				break;
			}
			else if (searchArea[i].innerHTML.includes("<h1>Customer Login</h1>")) {
				searchArea[i].style.display = "block";
				found.push(i);
				console.log("seller 2");
				break;
			}
			else if (searchArea[i].innerHTML.includes("Search your")) {
				searchArea[i].style.display = "block";
				found.push(i);
				console.log("seller 3");
				break;
			}
			else{
				console.log("seller 4");
				break;
			}*/

		}

		for (let i = 0; i < searchArea.length; i++) {
			if (!found.includes(i)) {
				searchArea[i].style.display = "none";
			}
		}
	}

	close_menu();
}

const close_search = (el) => {
	var element = el;
	var searchArea = document.getElementsByClassName("search_area");
	var searchArea1 = document.getElementsByClassName("search_area1");
	var searchArea2 = document.getElementsByClassName("search_area2");
	var searchArea3 = document.getElementsByClassName("search_area3");
	var back = document.getElementsByClassName("back");
	var shopSection = document.getElementsByClassName("shop_section");
	var cBtn = document.getElementsByClassName("c_btn");
	console.log(cBtn.length);
	if (shopSection.length > 0) {
		for (let i = 0; i < shopSection.length; i++) {
			for (let j = 0; j < shopSection[i].getElementsByTagName("div").length; j++) {
				shopSection[i].getElementsByTagName("div")[j].style.zIndex = "5000";
			}
		}
	}
	if (searchArea.length > 0) {
		for (let i = 0; i < searchArea.length; i++) {
			if (window.getComputedStyle(searchArea[i]).display === "block") {
				searchArea[i].style.display = "none";
			}
		}
	}
	if (searchArea1.length > 0) {
		for (let i = 0; i < searchArea1.length; i++) {
			if (window.getComputedStyle(searchArea1[i]).display === "block") {
				searchArea1[i].style.display = "none";
			}
		}
	}
	if (cBtn[0] === element) {
		console.log("hello");
		if (searchArea2.length > 0) {
			for (let i = 0; i < searchArea2.length; i++) {
				if (window.getComputedStyle(searchArea2[i]).display === "block") {
					searchArea2[i].style.display = "none";
					for (let j = 0; j < back.length; j++) {
						back[j].style.display = "inline";
					}

				}
			}
		}
	}
	else {
		if (searchArea3.length > 0) {
			for (let i = 0; i < searchArea3.length; i++) {
				if (window.getComputedStyle(searchArea3[i]).display === "block") {
					searchArea3[i].style.display = "none";
					for (let j = 0; j < back.length; j++) {
						back[j].style.display = "inline";
					}

				}
			}
		}
	}

}
function login_opt() {

	var loginOpt = document.getElementsByClassName("login_opt")[0];
	if (window.getComputedStyle(loginOpt).display === "block") {
		loginOpt.style.display = "none";
	}
	else {
		loginOpt.style.display = "block";
	}

}
/*const show_seller_login = () => {
	var searchArea = document.getElementsByClassName("search_area");
	if (searchArea.length > 0) {
		for (let i = 0; i < searchArea.length; i++) {
			if (searchArea[i].innerHTML.includes("Seller Login")) {
				searchArea[i].style.display = "block";
			}
		}
	}
	close_menu();
}*/
const close_menu = () => {
	var close = document.getElementsByClassName("m_menu")[0];
	if (close != null) {
		close.click();
		console.log("close");
	}


}
const show_register = (el) => {
	var element = el;
	var searchArea = document.getElementsByClassName("search_area");
	var searchArea1 = document.getElementsByClassName("search_area1");
	var shopSection = document.getElementsByClassName("shop_section");
	var showRegister = document.getElementsByClassName("show_register");
	if (shopSection.length > 0) {
		for (let i = 0; i < shopSection.length; i++) {
			for (let j = 0; j < shopSection[i].getElementsByTagName("div").length; j++) {
				shopSection[i].getElementsByTagName("div")[j].style.zIndex = "0";
			}
		}
	}

	if (searchArea.length > 0) {

		for (let i = 0; i < searchArea.length; i++) {
			if (window.getComputedStyle(searchArea[i]).display === "block") {

				searchArea[i].style.display = "none";
			}
		}
	}
	if (searchArea1.length > 0) {
		for (let i = 0; i < showRegister.length; i++) {
			if (showRegister[i] === element) {
				searchArea1[i].style.display = "block";
			}
			else {
				searchArea1[i].style.display = "none";
			}
		}
		/*for (let i = 0; i < searchArea1.length; i++) {
			if (window.getComputedStyle(searchArea1[i]).display === "none") {

				searchArea1[i].style.display = "block";
			}
		}*/
	}
}

const show_add_product = (el) => {
	var element = el;
	var addProduct = document.getElementsByClassName("add_product");
	var addP = document.getElementById("add_id");
	var addCheck = document.getElementById("add_check_id");
	var addP1 = document.getElementById("add_id1");
	var addCheck1 = document.getElementById("add_check_id1");
	var searchArea = document.getElementsByClassName("search_area");
	var searchArea2 = document.getElementsByClassName("search_area2");
	var searchArea3 = document.getElementsByClassName("search_area3");
	var shopSection = document.getElementsByClassName("shop_section");
	var back = document.getElementsByClassName("back");
	console.log("length :" + addProduct.length);
	if (addProduct[0] === element) {
		console.log("here1");
		console.log(addCheck.value);
		if (addCheck.value === "add1") {
			addP.value = "add2";
		}
		else {
			addP.value = "add1";
		}
		console.log(addP.value);
		if (shopSection.length > 0) {
			for (let i = 0; i < shopSection.length; i++) {
				for (let j = 0; j < shopSection[i].getElementsByTagName("div").length; j++) {
					shopSection[i].getElementsByTagName("div")[j].style.zIndex = "0";
				}
			}
		}

		if (searchArea.length > 0) {

			for (let i = 0; i < searchArea.length; i++) {
				if (window.getComputedStyle(searchArea[i]).display === "block") {

					searchArea[i].style.display = "none";
				}
			}
		}
		if (searchArea2.length > 0) {

			for (let i = 0; i < searchArea2.length; i++) {
				if (window.getComputedStyle(searchArea2[i]).display === "none") {

					searchArea2[i].style.display = "block";
					for (let j = 0; j < back.length; j++) {
						back[j].style.display = "none";
					}
				}
			}
		}
		if (searchArea3.length > 0) {

			for (let i = 0; i < searchArea3.length; i++) {
				if (window.getComputedStyle(searchArea3[i]).display === "block") {

					searchArea3[i].style.display = "none";


				}
			}
		}
	}
	else {
		console.log("here2");
		if (addCheck1.value === "add1") {
			addP1.value = "add2";
		}
		else {
			addP1.value = "add1";
		}
		console.log(addP.value);
		if (shopSection.length > 0) {
			for (let i = 0; i < shopSection.length; i++) {
				for (let j = 0; j < shopSection[i].getElementsByTagName("div").length; j++) {
					shopSection[i].getElementsByTagName("div")[j].style.zIndex = "0";
				}
			}
		}

		if (searchArea.length > 0) {

			for (let i = 0; i < searchArea.length; i++) {
				if (window.getComputedStyle(searchArea[i]).display === "block") {

					searchArea[i].style.display = "none";
				}
			}
		}
		if (searchArea2.length > 0) {

			for (let i = 0; i < searchArea2.length; i++) {
				if (window.getComputedStyle(searchArea2[i]).display === "block") {

					searchArea2[i].style.display = "none";


				}
			}
		}
		if (searchArea3.length > 0) {

			for (let i = 0; i < searchArea3.length; i++) {
				if (window.getComputedStyle(searchArea3[i]).display === "none") {

					searchArea3[i].style.display = "block";
					for (let j = 0; j < back.length; j++) {
						back[j].style.display = "none";
					}

				}
			}
		}
	}

}
var url = [];
const displaySellerRegistration = () => {
	console.log("seller");
	//var aGreeting = document.getElementById("a_greeting");
	//var inputs = document.getElementsByTagName("input");
	var lc = document.getElementById("length_check");
	var lc1 = document.getElementById("length_check1");
	var addProduct = document.getElementsByClassName("add_product");
	var searchArea1 = document.getElementsByClassName("search_area1");
	/*var forms = document.getElementsByClassName("add_product_form") ;
	console.log("forms :"+forms.length);*/
	var credChecker = document.getElementById("cred_checker");
	var credChecker1 = document.getElementById("cred_checker1");
	var credChecker3 = document.getElementById("cred_checker3");
	var credChecker4 = document.getElementById("cred_checker4");
	var credChecker5 = document.getElementById("cred_checker5");
	var s1 = document.getElementsByClassName("s1");
	var regBtn = document.getElementById("s_register_btn");
	var regBtn1 = document.getElementById("c_register_btn");
	var showId = document.getElementById("show_id");
	var jumpLink = document.getElementById("jump_link1");
	var viewSpanId1 = document.getElementById("view_span_id1");
	var viewSpanId2 = document.getElementById("view_span_id2");
	var jumpLink2 = document.getElementById("jump_link1");
	var jumpLink3 = document.getElementById("jump_link3");
	var customerEmailId1 = document.getElementById("customer_email_id1");
	var customerEmailId = document.getElementById("customer_email_id");
	var customerLoggedInBtn = document.getElementById("customer_logged_in_btn");
	var productAdded = document.getElementById("product_added_id");
	var addToCartBtn = document.getElementsByClassName("add_to_cart_btn")[0];
	var removedId = document.getElementById("removed_id");
	var cartTopDiv = document.getElementsByClassName("cart_top_div")[0];
	var manageCartId = document.getElementById("manage_cart_id");
	var searchInput = document.getElementById("in_search_item");
	const mq = window.matchMedia("(max-width:600px)");
	//cartTopDiv.style.marginLeft = "60%"
	//var catDisplayId = document.getElementById("cat_display_id");
	//var backB = document.getElementById("backB");
	//var sLoginBtn = document.getElementsByClassName("seller_login_btn1")[0];
	//var showCatItems = document.getElementsByClassName("show_cat_items")[0];
	if (credChecker !== null) {
		if (credChecker.value === "bad") {
			console.log("seller");
			if (searchArea1.length > 0) {

				for (let i = 0; i < searchArea1.length; i++) {
					if (searchArea1[i].innerHTML.includes("Bussiness Details")) {
						if (window.getComputedStyle(searchArea1[i]).display === "none") {
							console.log("seller");
							searchArea1[i].style.display = "block";
						}
					}
					else {
						if (window.getComputedStyle(searchArea1[i]).display === "block") {
							console.log("seller");
							searchArea1[i].style.display = "none";
						}
					}
				}
			}
			swal({
				title: "Oops!",
				text: "Please fill the fields correctly",
				icon: "error",
				button: "Try Again!",
			});
		}
		else if (credChecker.value === "good") {
			swal({
				title: "Registration Completed!",
				text: "You have registered successfully!",
				icon: "success",
				button: "OK!",
			}).then(() => {
				regBtn.click();
			});

		}
		else if (credChecker.value === "incomplete") {
			swal({
				title: "Step 1 completed !",
				text: "You have to fill kyc form for start adding your products ! Are you ready ?",
				icon: "warning",
				buttons: true,
				dangerMode: true,
			})
				.then((open) => {
					if (open) {
						for (let i = 0; i < searchArea1.length; i++) {
							if (searchArea1[i].innerHTML.includes("Bank")) {
								searchArea1[i].style.display = "block";
							}
							else {
								searchArea1[i].style.display = "none";
							}
						}
					}
				});
		}
		else if (credChecker.value === "email_verified") {
			$(".search_area1").each(function(){
				if($(this).html().includes("Verify Email")){
					$(this).show();
				}
				else{
					$(this).hide();
				}
			});
			$(".verify_btns").show();
			$(".verify_btns .btn1").css({"cursor": "not,allwed",
				"opacity":"0.5"
			}).html("Email Verified <i class='fa-solid fa-check'></i>");
			$(".mail_otp_div").hide();
			swal({
				title: "Success !",
				text: "Email verification done successfully !!",
				icon: "success",
				button: "Continue",
			});

		}
	}
	if (credChecker5 !== null) {
		if (credChecker5.value === "ba_bad") {
			console.log("seller");
			if (searchArea1.length > 0) {

				for (let i = 0; i < searchArea1.length; i++) {
					if (searchArea1[i].innerHTML.includes("Bank Account")) {
						if (window.getComputedStyle(searchArea1[i]).display === "none") {
							console.log("seller");
							searchArea1[i].style.display = "block";
						}
					}
					else {
						if (window.getComputedStyle(searchArea1[i]).display === "block") {
							console.log("seller");
							searchArea1[i].style.display = "none";
						}
					}
				}
			}
			swal({
				title: "Oops!",
				text: "Please fill the fields correctly",
				icon: "error",
				button: "Try Again!",
			});
		}
		/*else if (credChecker.value === "ba_good") {
			swal({
				title: "Registration Completed!",
				text: "You have registered successfully!",
				icon: "success",
				button: "OK!",
			}).then(() => {
				regBtn.click();
			});

		}*/
		else if (credChecker.value === "pending") {
			swal({
				title: "Details Submitted !",
				text: "Your account will be activated after verifying your kyc details",
				icon: "warning",
				button: "OK!",
			});

		}
	}
	if (credChecker3 !== null) {
		if (credChecker3.value === "bad") {
			console.log("customer");
			if (searchArea1.length > 0) {

				for (let i = 0; i < searchArea1.length; i++) {
					if (searchArea1[i].innerHTML.includes("Customer Registration")) {
						if (window.getComputedStyle(searchArea1[i]).display === "none") {
							console.log("seller");
							searchArea1[i].style.display = "block";
						}
					}
					else {
						if (window.getComputedStyle(searchArea1[i]).display === "block") {
							console.log("seller");
							searchArea1[i].style.display = "none";
						}
					}

				}
			}
			swal({
				title: "Oops!",
				text: "Please fill the fields correctly",
				icon: "error",
				button: "Try Again!",
			});
		}
		else if (credChecker3.value === "good") {
			swal({
				title: "Registration Completed!",
				text: "You have registered successfully!",
				icon: "success",
				button: "OK!",
			}).then(() => {
				regBtn1.click();
			});

		}
	}
	if (credChecker1 != null) {
		if (credChecker1.value === "bad") {
			if (s1.length > 0) {
				for (let i = 0; i < s1.length; i++) {
					if (s1[i].innerHTML === "Seller") {
						s1[i].click();
					}
				}
			}
			/*if (s1.length > 0) {
				for (let i = 0; i < s1.length; i++) {
					if (s1[i].innerHTML === "Seller") {
						s1[i].click();
					}
				}
			}
			var loginOpt = document.getElementsByClassName("login_opt")[0];
			if (window.getComputedStyle(loginOpt).display === "block") {
				loginOpt.style.display = "none";
			}*/
			swal({
				title: "Oops!",
				text: "Invalid username or password",
				icon: "error",
				button: "Try Again!",
			});

		}

	}
	if (credChecker4 != null) {
		if (credChecker4.value === "bad") {
			if (s1.length > 0) {
				for (let i = 0; i < s1.length; i++) {
					if (s1[i].innerHTML === "Customer") {
						s1[i].click();
					}
				}
			}
			/*if (s1.length > 0) {
				for (let i = 0; i < s1.length; i++) {
					if (s1[i].innerHTML === "Seller") {
						s1[i].click();
					}
				}
			}
			var loginOpt = document.getElementsByClassName("login_opt")[0];
			if (window.getComputedStyle(loginOpt).display === "block") {
				loginOpt.style.display = "none";
			}*/
			swal({
				title: "Oops!",
				text: "Invalid username or password",
				icon: "error",
				button: "Try Again!",
			});

		}

	}
	/*if (credChecker.value === "bad") {
		console.log("seller");
		if (searchArea1.length > 0) {
	
			for (let i = 0; i < searchArea1.length; i++) {
				if (window.getComputedStyle(searchArea1[i]).display === "none") {
					console.log("seller");
					searchArea1[i].style.display = "block";
				}
			}
		}
		swal({
			title: "Oops!",
			text: "Please fill the fields correctly",
			icon: "error",
			button: "Try Again!",
		});
	}
	else if (credChecker.value === "good") {
		swal({
			title: "Registration Completed!",
			text: "You have registered successfully!",
			icon: "success",
			button: "OK!",
		}).then(() => {
			window.location.href = "/"
			window.location.href = "/seller/add_item"
		});
	
	}*/
	/*else{
		console.log("seller");
		if (searchArea1.length > 0) {
	
			for (let i = 0; i < searchArea1.length; i++) {
				searchArea1[i].style.display = "none";
				if (window.getComputedStyle(searchArea1[i]).display === "block") {
					console.log("seller");
					searchArea1[i].style.display = "none";
				}
			}
		}
	}*/

	/*if(showCatItems != null){
		if(!showCatItems.classList.contains("cat_items_display")){
				showCatItems.classList.add("cat_items_display");
			}
	}*/
	/*if(url.length===0){
		if(window.location.href.includes("seller")){
			url.push("seller");
		}
	}
	console.log(url.length);*/
	/*if (window.location.href.includes("add_item")) {
		localStorage.setItem("location", "seller");
		console.log(localStorage.getItem("location"));
	}
	else if (window.location.href.includes("do_login")) {
	
		//if (!swal.isOpened()) {
		var backB = document.getElementById("backB");
		if (backB.value === "true") {
			localStorage.setItem("location", "seller");
			console.log(localStorage.getItem("location"));
			console.log("no swal");
		}
	
		console.log(localStorage.getItem("location"));
		//}
	}
	else if (!window.location.href.includes("do_login")) {
		console.log(localStorage.getItem("location"));
		console.log("location");
		if (localStorage.getItem("location") === "seller") {
			window.location.href = "/";
			localStorage.removeItem("location")
		}
		else {
			console.log("failed");
		}
	}*/
	/*if(backB !== null){
		if(backB.value === "true"){
			window.location.href="/";
			sLoginBtn.click();
		}
		else if(backB.value === ""){
			window.location.href="/";
			
		}
		
	}*/
	//console.log(backB.value);
	/*if(!aGreeting){
		
		
			window.history.back();
	}*/
	if (lc != null) {
		if (lc.value === "change") {
			addProduct[0].style.marginTop = "0%";
			console.log("her1");
		}
		if (lc1.value === "change") {
			addProduct[1].style.marginTop = "0%";
			console.log("her2");
		}
	}
	console.log(showId);
	if (showId != null) {
		if (showId.value) {
			jumpLink.click();
		}

	}
	if (viewSpanId1 != null) {
		jumpLink2.click();
		if (viewSpanId1.value) {

		}

		/*console.log("link1")
		if (viewSpanId1.value) {
			console.log("link1")
			
		}
		else if (viewSpanId2.value) {
			console.log("link1")
			jumpLink3.click();
		}*/
	}
	else if (viewSpanId2 != null) {
		jumpLink3.click();
		if (viewSpanId2.value) {

		}
	}
	if (customerEmailId1 !== null) {
		console.log(customerEmailId1.value);
		localStorage.setItem("user", customerEmailId1.value);
		console.log(localStorage.getItem("user"));
	}
	if (customerEmailId !== null) {
		console.log(customerEmailId.value);
		console.log("user:" + localStorage.getItem("user"));
		if (localStorage.getItem("user") !== null) {
			customerEmailId.value = localStorage.getItem("user");
			customerLoggedInBtn.click();
		}

	}
	if (productAdded != null) {
		//if (!addToCartBtn.disabled) {
		addToCartBtn.disabled = true;
		addToCartBtn.style.opacity = "0.5";
		addToCartBtn.style.cursor = "not-allowed";
		//cartTopDiv.style.marginLeft = "60%";
		//console.log(manageCartId.value);
		//if (manageCartId != null) {
		//if (manageCartId.value === "submit") {
		/*if (localStorage.getItem("cart_status")) {
			if (mq.matches) {
				cartTopDiv.style.marginLeft = "10%";
			}
			else {
				cartTopDiv.style.marginLeft = "60%";
			}
		}*/

		manageCartId.value = "no submit";
		//}

		//}


	}



	if (removedId !== null) {
		/*console.log("reached hre");
		console.log("removed"+removedId.value);*/
		addToCartBtn.disabled = false;
		addToCartBtn.style.opacity = "1";
		addToCartBtn.style.cursor = "pointer";
		/*if (localStorage.getItem("cart_status")) {
			if (mq.matches) {
				cartTopDiv.style.marginLeft = "10%";
			}
			else {
				cartTopDiv.style.marginLeft = "60%";
			}
		}*/

	}
	console.log(localStorage.getItem("cart_status"));
	if (localStorage.getItem("cart_status")) {
		if (mq.matches) {
			cartTopDiv.style.marginLeft = "10%";
		}
		else {
			cartTopDiv.style.marginLeft = "60%";
		}
	}
	/*if(searchInput != null){
		if(searchInput.value){
			searchInput.focus();
			var temp = searchInput.value;
			searchInput.value = "";
			searchInput.value = temp;
		}
	}*/
	/*if(catDisplayId != null){
		if(catDisplayId === "no_display"){
			close_item_list();
		}
	}*/

	$(".select1 option").each(function() {
		if ($(this).val() == $("#bussinessType_id").val()) {
			$(this).attr("selected", "selected");
		}
	});
	if($("#mail_otp_id").length){
		$(".verification_area").show();
		$(".verification_area .verify_btns").hide();
		$(".verification_area .mail_otp_div").show();
	}
	
	console.log(window.location.href);
}
/*end display seller*/
//}
const suggest = () => {
	console.log("suggest");
	const center = { lat: 28.6691255, lng: 77.0928656 };
	// Create a bounding box with sides ~10km away from the center point
	const defaultBounds = {
		north: center.lat + 0.1,
		south: center.lat - 0.1,
		east: center.lng + 0.1,
		west: center.lng - 0.1,
	};
	const input = document.getElementsByClassName("street1")[0];
	const options = {
		bounds: defaultBounds,
		componentRestrictions: { country: "in" },
		fields: ["address_components", "geometry", "icon", "name"],
		strictBounds: false,
	};
	const autocomplete = new google.maps.places.Autocomplete(input, options);
	console.log(autocomplete);
}
const displayAddProduct = () => {
	console.log("seller");
	var searchArea2 = document.getElementsByClassName("search_area2");
	//var credChecker = document.getElementById("cred_checker2");

	//if (credChecker.value === "bad") {
	console.log("seller");
	if (searchArea2.length > 0) {

		for (let i = 0; i < searchArea2.length; i++) {
			if (window.getComputedStyle(searchArea2[i]).display === "none") {
				console.log("seller");
				searchArea2[i].style.display = "block";
			}
		}
	}
	/*swal({
		title: "Oops!",
		text: "Please fill the fields correctly",
		icon: "error",
		button: "Try Again!",
	});*/
	//}
	//else if (credChecker.value === "good") {
	/*swal({
		title: "Registration Completed!",
		text: "You have registered successfully!",
		icon: "success",
		button: "OK!",
	}).then(() => {
		window.location.href = "/"
		window.location.href = "/seller/add_item"
	});*/

	//}
	/*else{
		console.log("seller");
		if (searchArea1.length > 0) {
	
			for (let i = 0; i < searchArea1.length; i++) {
				searchArea1[i].style.display = "none";
				if (window.getComputedStyle(searchArea1[i]).display === "block") {
					console.log("seller");
					searchArea1[i].style.display = "none";
				}
			}
		}
	}*/
}
var check1 = 0;
const file_choosen = () => {
	var choice1 = document.getElementsByClassName("choice1");
	//var pName = document.getElementById("p_name");
	//var srb = document.getElementById("seller_register_btn");
	var file = document.getElementById("file");
	var sFile = document.getElementById("s_file");
	if (file.files.length > 0) {
		console.log("hello");
		if (window.getComputedStyle(choice1[0]).display === "none") {
			console.log("seller");
			choice1[0].style.display = "block";
		}
		file.removeAttribute("onchange");
		/*srb.setAttribute("type", "button");
		srb.setAttribute("onclick", "try_again()");*/
		/*if (pName.value !== "") {
			srb.setAttribute("type", "button");
			srb.setAttribute("onclick", "try_again()");
			check1 = 1;
		}*/
	}
	if (sFile.files.length > 0) {
		console.log("hello");
		if (window.getComputedStyle(choice1[1]).display === "none") {
			console.log("seller");
			choice1[1].style.display = "block";
		}
		sFile.removeAttribute("onchange");
		/*srb.setAttribute("type", "button");
		srb.setAttribute("onclick", "try_again()");*/
		/*if (pName.value !== "") {
			srb.setAttribute("type", "button");
			srb.setAttribute("onclick", "try_again()");
			check1 = 1;
		}*/
	}
}
const file_choosen1 = () => {
	var choice1 = document.getElementsByClassName("choice1");
	for (let i = 0; i < choice1.length; i++) {
		if (window.getComputedStyle(choice1[i]).display = "block") {
			choice1[i].style.display = "none";
		}
	}
}


const choice1_yes = (el) => {
	/*var searchArea2 = document.getElementsByClassName("search_area2")[0];*/
	var element = el;
	var choiceBtn = document.getElementsByClassName("choice1_yes_btn");
	var choice1 = document.getElementsByClassName("choice1");
	var nImages = document.getElementsByClassName("n_images");
	var imgInput1 = document.getElementsByClassName("img_input1");
	//var srb = document.getElementById("seller_register_btn");
	for (let i = 0; i < choiceBtn.length; i++) {
		if (choiceBtn[i] === element) {
			choice1[i].style.display = "none";
			nImages[i].style.display = "block";
			imgInput1[i].setAttribute("required", "true");
		}
	}

	/*srb.setAttribute("type", "submit");
	srb.removeAttribute("onclick");*/

}
const choice3_yes = (el) => {
	var element = el;
	var choiceBtn = document.getElementsByClassName("choice3_yes_btn");
	var choiceBtn1 = document.getElementsByClassName("choice1_yes_btn");
	for (let i = 0; i < choiceBtn.length; i++) {
		if (choiceBtn[i] === element) {
			choice1_yes(choiceBtn1[i]);
		}
	}

}
const choice1_no = (el) => {
	var element = el;
	/*var searchArea2 = document.getElementsByClassName("search_area2")[0];*/
	//var srb = document.getElementById("seller_register_btn");
	//var choiceBtn = document.getElementsByClassName("choice1_no_btn");
	var choice1 = document.getElementsByClassName("choice1");
	var nImages1 = document.getElementsByClassName("n_images1");
	var featureInput1 = document.getElementsByClassName("feature_input1");
	var choiceBtn = document.getElementsByClassName("choice1_no_btn");
	for (let i = 0; i < choiceBtn.length; i++) {
		if (choiceBtn[i] === element) {
			choice1[i].style.display = "none";
			/*srb.setAttribute("type", "submit");
			srb.removeAttribute("onclick");*/
			nImages1[i].style.display = "block";
			featureInput1[i].setAttribute("required", "true");
		}
	}

	choice2_yes();
}
const choice2_yes = (el) => {
	/*var searchArea2 = document.getElementsByClassName("search_area2")[0];*/
	//var choice1 = document.getElementsByClassName("choice1")[0];
	var element = el;
	var choiceBtn = document.getElementsByClassName("choice2_yes_btn");
	var nImages1 = document.getElementsByClassName("n_images1");
	var naviLink1 = document.getElementById("navi_link1");
	var naviLink2 = document.getElementById("navi_link2");
	var featureInput1 = document.getElementsByClassName("feature_input1");
	//choice1.style.display = "none";
	for (let i = 0; i < choiceBtn.length; i++) {
		if (choiceBtn[i] === element) {
			nImages1[i].style.display = "block";
			featureInput1[i].setAttribute("required", "true");
			if (i === 0) {
				if (naviLink1 !== null) {
					naviLink1.click();
				}
			}
			else {
				if (naviLink2 !== null) {
					naviLink2.click();
				}
			}

		}
	}


}
var add_more = 0;
const proceed1 = (el) => {

	var element = el;
	var proceedBtn = document.getElementsByClassName("proceed1_btn");
	var subImages = document.getElementsByClassName("sub_images");
	var nImages = document.getElementsByClassName("n_images");
	var imgInput1 = document.getElementsByClassName("img_input1");
	var addImgDiv = document.getElementsByClassName("add_img_div");
	var choice1 = document.getElementsByClassName("choice1");
	console.log(proceedBtn);
	console.log(subImages.length);
	console.log(nImages.length);

	for (let i = 0; i < proceedBtn.length; i++) {
		console.log(i);
		if (proceedBtn[i] === element) {
			console.log("i:" + i)
			var n = Number(imgInput1[i].value);
			if (n < 1) {
				swal({
					title: "Invalid value !",
					text: "Please enter the number of images you want to add or cancel the option",
					icon: "error",
					button: "Try Again!",
				});
				return;
			}
			//i += 1;
			for (let j = 0; j < n; j++) {
				subImages[i].innerHTML += `<div class="input_container2">
						<div class="file_chooser2">
							<label for="file${i}">choose sub image <span class="count">${j + 1}</span></label>
							<input type="file" id="file${j}" class="file" onchange="file_choosen1()" 
							name="sub_image" accept="image/jpeg,image/png" required/>
							<i class="fa-solid fa-xmark close_btn4 cross" onclick="delete_img(this)"></i>
						</div>
						<hr />
					</div>
					
				 `
			}
			add_more = n;
			addImgDiv[i].style.display = "block";
			nImages[i].remove();
			choice1[i].style.display = "none";
			imgInput1[i].removeAttribute("required");
		}
	}

	//console.log(i);
}
const add_img = (el) => {
	add_more += 1;
	var element = el;
	var subImages = document.getElementsByClassName("sub_images");
	var addImgBtn = document.getElementsByClassName("add_img_btn");
	/*subImages1.innerHTML += `<div class="input_container2">
						<div class="file_chooser2">
							<label for="file${add_more}">choose sub image image <span class="count">${add_more}</label>
							<input type="file" id="file${add_more}" class="file" onchange="file_choosen()" accept="image/jpeg,image/png"/>
							<i class="fa-solid fa-xmark close_btn3 cross" onclick="delete_img(this)"></i>
						</div>
						<hr />
					</div>
					
				 `*/
	for (let i = 0; i < addImgBtn.length; i++) {
		if (addImgBtn[i] === element) {
			var element1 = document.createElement("div");
			element1.setAttribute("class", "input_container2");
			subImages[i].appendChild(element1);

			var element2 = document.createElement("div");
			element2.setAttribute("class", "file_chooser2");
			element1.appendChild(element2);

			var element3 = document.createElement("label");
			element3.setAttribute("for", "file" + add_more);
			var node3 = document.createTextNode("choose sub image ");
			element3.appendChild(node3);
			var span3 = document.createElement("span");
			span3.setAttribute("class", "count");
			var span3_node = document.createTextNode(add_more);
			span3.appendChild(span3_node);
			element3.appendChild(span3);
			element2.appendChild(element3);



			var element4 = document.createElement("input");
			element4.setAttribute("type", "file");
			element4.setAttribute("id", "file" + add_more);
			element4.setAttribute("class", "file");
			element4.setAttribute("name", "sub_image");
			element4.setAttribute("accept", "image/jpeg,image/png");
			element4.setAttribute("required", "true");
			element2.appendChild(element4);

			var element5 = document.createElement("i");

			element5.setAttribute("class", "fa-solid fa-xmark close_btn3 cross");
			element5.setAttribute("onclick", "delete_img(this)");

			element2.appendChild(element5);

			var element6 = document.createElement("hr");
			element1.appendChild(element6);
		}
	}

}
const delete_img = (el) => {

	var element = el;
	var cross = document.getElementsByClassName("cross");
	var inputContainer2 = document.getElementsByClassName("input_container2");
	var count = document.getElementsByClassName("count");
	if (cross.length > 0) {
		for (let i = 0; i < cross.length; i++) {
			if (cross[i] === element) {
				inputContainer2[i].remove();
				add_more -= 1;
			}
		}
	}
	for (let i = 0; i < add_more; i++) {

		console.log(count.length);
		count[i].innerHTML = i + 1;
	}

}
var add_more1 = 0;
const proceed2 = (el) => {
	var element = el;
	var proceedBtn = document.getElementsByClassName("proceed2_btn");
	var addProductBtn = document.getElementsByClassName("add_product_btn");
	var addProductForm = document.getElementsByClassName("add_product_form");
	var features = document.getElementsByClassName("features");
	var nImages1 = document.getElementsByClassName("n_images1");
	var featureInput1 = document.getElementsByClassName("feature_input1");
	var addFeatureDiv = document.getElementsByClassName("add_feature_div");
	for (let i = 0; i < proceedBtn.length; i++) {
		if (proceedBtn[i] === element) {
			var n = Number(featureInput1[i].value);
			//i += 1;
			for (let j = 0; j < n; j++) {
				features[i].innerHTML += `<div class="n_images2">
					<label>Feature <span class="count1">${j + 1}</span></label>
					<i class="fa-solid fa-xmark close_btn3 cross1" onclick="delete_feature(this)"></i>
					<input name="features" type="text" class="search_input1 feature_input1"
						 required />
					
					
					<hr />

				</div>
					
				 `
			}
			add_more1 = n;
			addFeatureDiv[i].style.display = "block";
			nImages1[i].remove();
			featureInput1[i].removeAttribute("required");
			addProductBtn[i].style.opacity = "1";
			addProductBtn[i].style.cursor = "pointer";
			addProductForm[i].removeAttribute("onkeydown");
			//console.log(i);
		}
	}

}
const add_feature = (el) => {

	add_more1 += 1;
	var element = el;
	var addFeatureBtn = document.getElementsByClassName("add_feature_btn");
	var addProductBtn = document.getElementsByClassName("add_product_btn");
	var addProductForm = document.getElementsByClassName("add_product_form");
	var features = document.getElementsByClassName("features");
	/*features.innerHTML += `<div class="n_images2">
					<label>Feature <span class="count1">${add_more1}</span></label>
					
					<input type="text" class="search_input1 feature_input1"
						 />
					<i class="fa-solid fa-xmark close_btn3 cross1" onclick="delete_feature(this)"></i>
					
					<hr />
	
				</div>
					
				 `*/
	for (let i = 0; i < addFeatureBtn.length; i++) {
		if (addFeatureBtn[i] === element) {
			var element1 = document.createElement("div");
			element1.setAttribute("class", "n_images2");
			features[i].appendChild(element1);



			var element2 = document.createElement("label");

			var node2 = document.createTextNode("Feature ");
			element2.appendChild(node2);
			var span2 = document.createElement("span");
			span2.setAttribute("class", "count1");
			var span2_node = document.createTextNode(add_more1);
			span2.appendChild(span2_node);
			element2.appendChild(span2);
			element1.appendChild(element2);

			var element4 = document.createElement("i");

			element4.setAttribute("class", "fa-solid fa-xmark close_btn3 cross1");
			element4.setAttribute("onclick", "delete_feature(this)");

			element1.appendChild(element4);

			var element3 = document.createElement("input");
			element3.setAttribute("type", "text");

			element3.setAttribute("class", "search_input1 feature_input1");
			element3.setAttribute("name", "features");

			element3.setAttribute("required", "true");
			element1.appendChild(element3);



			var element5 = document.createElement("hr");
			element1.appendChild(element5);

			addProductBtn[i].style.opacity = "1";
			addProductBtn[i].style.cursor = "pointer";
			addProductForm[i].removeAttribute("onkeydown");

		}
	}

}
const delete_feature = (el) => {
	console.log("delte");
	var element = el;
	var cross1 = document.getElementsByClassName("cross1");
	var nImages2 = document.getElementsByClassName("n_images2");
	var count1 = document.getElementsByClassName("count1");
	var addProductBtn = document.getElementById("add_product_btn");
	var addProductForm = document.getElementById("add_product_form");
	if (cross1.length > 0) {
		for (let i = 0; i < cross1.length; i++) {
			if (cross1[i] === element) {
				nImages2[i].remove();
				add_more1 -= 1;
			}
		}
	}
	for (let i = 0; i < add_more1; i++) {

		console.log(count1.length);
		count1[i].innerHTML = i + 1;
	}
	if (nImages2.length === 0) {
		addProductBtn.style.opacity = "0.5";
		addProductBtn.style.cursor = "not-allowed";
		addProductForm.setAttribute("onkeydown", "return event.key !='Enter'");
	}

}
const try_again = () => {
	swal({
		title: "Try Again !",
		text: "Please choose whether you want to add more images or not",
		icon: "error",
		button: "Try Again!",
	});
}

const close_img_input1 = (el) => {
	var element = el;
	var nImages = document.getElementsByClassName("n_images");
	var choice1 = document.getElementsByClassName("choice1");
	var closeBtn = document.getElementsByClassName("close_btn3");
	var imgInput1 = document.getElementsByClassName("img_input1");
	for (let i = 0; i < closeBtn.length; i++) {
		if (closeBtn[i] === element) {
			imgInput1[i].removeAttribute("required");
			choice1[i].style.display = "block";
			nImages[i].style.display = "none";
		}

	}

}
const click1 = (el) => {
	var element = el;
	var removeProductBtn = document.getElementsByClassName("remove_product_btn");
	var iRemove = document.getElementsByClassName("i_remove");
	for (let i = 0; i < iRemove.length; i++) {
		if (iRemove[i] === element) {
			removeProductBtn[i].click();
		}
	}
}

const view_cat_click = (el) => {
	var element = el;
	//var showCatItems = document.getElementsByClassName("show_cat_items")[0];
	var viewCat = document.getElementsByClassName("cat_div");
	//var viewCat2 = document.getElementById("view_cat2");
	var viewCat1 = document.getElementsByClassName("view_cat1");
	console.log("view click");
	for (let i = 0; i < viewCat.length; i++) {
		if (viewCat[i] === element) {
			console.log("view click");
			viewCat1[i].click();
			//showCatItems.style.cssText = "display:block!important";
			/*showCatItems.setAttribute("style","display:block!important");*/
			/*if(showCatItems.classList.contains("cat_items_display")){
				showCatItems.classList.remove("cat_items_display");
			}*/
			/*return;*/
		}
	}


}
/*const no_category = () => {
	swal({
					title: "Oops!",
					text: "Please select category",
					
					icon: "error",
					button: "Try Again!",
				});
}*/
/*window.onbeforeunload = function(e){
	e.returnValue = 'onbeforeunload';
	delete1();
	//return "are u sure";
}
function delete1(){
	console.log("delete()")
}*/
window.addEventListener("hashchange", function() {
	console.log("hello3")
});

const finish = () => {
	var cBtn = document.getElementById("ic1_sp1").getElementsByTagName("i")[0];
	var priceInput = document.getElementsByClassName("price_input")[0];
	var inputs = document.getElementsByClassName("search_input1");
	var specialForm = document.getElementById("special_form");
	//var finishBtn = document.getElementsByClassName("finish_btn")[0];
	var selChoice = document.querySelector("input[name='qty']:checked").id;
	var ic1sp1 = document.getElementById("ic1_sp1");
	var iQty = document.getElementById("i_qty");
	var ic3l3 = document.getElementById("ic1_l3");
	var ic1l2 = document.getElementById("ic1_l2");
	var ic1l1 = document.getElementById("ic1_l1");
	const mq = window.matchMedia("(max-width:600px)");
	//var ic1Sp1 = document.getElementById("ic1_l3");
	//var qtyH = document.getElementById("qty_h");
	specialForm.style.display = "none";
	ic1sp1.style.display = "inline";
	if (mq.matches) {
		priceInput.style.width = "50%";
	}
	else {
		priceInput.style.width = "60%";
	}


	//ic1l1.style.marginLeft="-1%";
	priceInput.removeAttribute("onfocus");
	cBtn.style.display = "inline";
	/*if(ic3l3.style.display !== "none"){
		qtyH.value = ic3l3.innerHTML;
	}
	else{
		qtyH.value = iQty.value;
	}*/
	ic1l2.style.display = "inline";
	if (selChoice === "r1") {
		iQty.style.display = "none";
		ic3l3.style.display = "inline";
		if (iQty.hasAttribute("required")) {
			iQty.removeAttribute("required");
		}

	}
	else {
		iQty.style.display = "inline";
		ic3l3.style.display = "none";
		priceInput.style.width = "50%";
		if (!iQty.hasAttribute("required")) {
			iQty.setAttribute("required", "true");
		}

	}
	for (let i = 0; i < inputs.length; i++) {

		inputs[i].disabled = false;
	}

}
const activate_finish = () => {
	var finishBtn = document.getElementsByClassName("finish_btn")[0];
	if (window.getComputedStyle(finishBtn).cursor === "not-allowed") {
		finishBtn.style.cursor = "pointer";
		finishBtn.style.opacity = "1";
	}
}
const priceFocus = () => {
	var ic1l2 = document.getElementById("ic1_l2");
	//console.log(ic1l2);
	var specialForm = document.getElementById("special_form");
	if (window.getComputedStyle(ic1l2).display === "none") {

		var inputs = document.getElementsByClassName("search_input1");
		console.log(inputs.length);
		for (let i = 0; i < inputs.length; i++) {

			inputs[i].disabled = true;
		}
		if (inputs[0].disabled) {
			specialForm.style.display = "block";
		}

	}

}
/*const priceBlur = () => {
	//var ic2l2 = document.getElementById("ic2_l2");
	var specialForm = document.getElementById("special_form");
	if (window.getComputedStyle(specialForm).display === "block") {
		specialForm.style.display = "none";
	}
	
}*/
const close_special_form = () => {
	var inputs = document.getElementsByClassName("search_input1");
	var specialForm = document.getElementById("special_form");
	specialForm.style.display = "none";
	for (let i = 0; i < inputs.length; i++) {

		inputs[i].disabled = false;
	}
}
const addQty = () => {
	var iQty = document.getElementById("i_qty");
	var ic3l3 = document.getElementById("ic1_l3");
	var qtyH = document.getElementById("qty_h");
	var submitForm = document.getElementById("submit_product_btn");
	if (ic3l3.style.display !== "none") {
		qtyH.value = ic3l3.innerHTML;
	}
	else {
		qtyH.value = iQty.value;
	}
	submitForm.click();
}
function hello() {
	console.log("hello");
}
const closeQty = () => {
	var finishBtn = document.getElementsByClassName("finish_btn")[0];
	var ic1l2 = document.getElementById("ic1_l2");
	var priceInput = document.getElementsByClassName("price_input")[0];
	var ic1sp1 = document.getElementById("ic1_sp1");
	var iQty = document.getElementById("i_qty");
	var qtyRadio = document.getElementsByClassName("qty_radio");
	const mq = window.matchMedia("(max-width:600px)");
	if (mq.matches) {
		priceInput.style.width = "85%";
	}
	else {
		priceInput.style.width = "80%";
	}
	ic1l2.style.display = "none";
	ic1sp1.style.display = "none";
	if (iQty.hasAttribute("required")) {
		iQty.removeAttribute("required");
	}
	for (let i = 0; i < qtyRadio.length; i++) {
		if (qtyRadio[i].checked) {
			qtyRadio[i].checked = false;
		}
	}

	priceInput.setAttribute("onfocus", "priceFocus()");
	if (window.getComputedStyle(finishBtn).cursor === "pointer") {
		finishBtn.style.cursor = "not-allowed";
		finishBtn.style.opacity = "0.5";
	}
	/*priceInput.onfocus = () =>{
		priceFocus();
	}*/
}
const show_ins = (el) => {
	var element = el;
	var iRemove = document.getElementsByClassName("i_remove");
	var remS = document.getElementsByClassName("rem_span");
	for (let i = 0; i < iRemove.length; i++) {
		if (iRemove[i] === element) {
			remS[i].style.display = "inline";
		}
	}
};

const p_submit = (el) => {
	//var count = 0;
	var element = el;
	var viewSpan = document.getElementsByClassName("view_span");
	//ar viewSpanId = document.getElementById("view_span_id");
	var pSubBtn = document.getElementsByClassName("p_submit_btn");

	console.log(viewSpan.length);
	console.log(pSubBtn.length);
	for (let i = 0; i < viewSpan.length; i++) {
		if (viewSpan[i] === element) {
			pSubBtn[i].click();
		}
	}


	//count += 1;
}
const s_submit = (el) => {
	//var count = 0;
	var element = el;
	var viewCat = document.getElementsByClassName("view_cat");
	v//ar viewSpanId = document.getElementById("view_span_id");
	var sSubBtn = document.getElementsByClassName("s_submit_btn");

	//console.log(viewSpan.length);
	//console.log(pSubBtn.length);
	for (let i = 0; i < viewSpan.length; i++) {
		if (viewCat[i] === element) {
			sSubBtn[i].click();
		}
	}


	//count += 1;
}
const img_switch = (el) => {
	var element = el;
	var displayImg = document.getElementsByClassName("display_img")[0];
	var displayImg1 = document.getElementsByClassName("display_img1");
	for (let i = 0; i < displayImg1.length; i++) {
		if (displayImg1[i] === element) {
			var src = displayImg.getAttribute("src");
			var src1 = displayImg1[i].getAttribute("src");
			var temp = src;
			src = src1;
			src1 = temp;
			displayImg.setAttribute("src", src);
			displayImg1[i].setAttribute("src", src1);
		}
	}
}

const submit_pro = (el) => {
	var element = el;
	var proName = document.getElementsByClassName("pro_name");
	var proSubmit = document.getElementsByClassName("pro_submit");
	console.log("product");
	for (let i = 0; i < proName.length; i++) {
		if (proName[i] === element) {
			proSubmit[i].click();
		}
	}
}
const submit_serv = (el) => {
	var element = el;
	var servName = document.getElementsByClassName("serv_name1");
	var servSubmit = document.getElementsByClassName("serv_submit");
	console.log("product");
	for (let i = 0; i < servName.length; i++) {
		if (servName[i] === element) {
			servSubmit[i].click();
		}
	}
}
const close_item = (el) => {
	var element = el;
	var itemTopDiv = document.getElementsByClassName("item_top_div");
	var itemClose = document.getElementsByClassName("item_close_cross");
	console.log("hello");
	console.log(itemTopDiv.length);
	console.log(itemClose.length);
	for (let i = 0; i < itemClose.length; i++) {
		console.log("hello1");
		if (itemClose[i] === element) {
			console.log("hello2");
			itemTopDiv[i].style.display = "none";
		}
	}

}
const back_to_home = () => {
	window.location.href = "/";
}
const close_item_list = () => {
	var itemList = document.getElementsByClassName("cat_top_div")[0];
	itemList.style.display = "none";
}
const display_mobile_menu = () => {

	var mobileMenu1 = document.getElementsByClassName("mobile_menu1")[0];
	if (mobileMenu1.classList.contains("animation1")) {
		mobileMenu1.classList.remove("animation1");
		mobileMenu1.classList.add("animation2");
		console.log("hello10");
		//mobileMenu1.style.display="none";
	}
	else {
		console.log("hello11");
		if (mobileMenu1.classList.contains("animation2")) {
			mobileMenu1.classList.remove("animation2");
		}
		//mobile
		mobileMenu1.classList.add("animation1");
		mobileMenu1.style.display = "block";
	}
	/*if (window.getComputedStyle(mobileMenu1).display === "none") {
		mobileMenu1.style.display = "block";
	}
	else {
		mobileMenu1.style.display = "none";
	}*/
}
const logout = () => {
	console.log("logout");
	localStorage.removeItem("user");
	console.log("logout");
	window.location.href = "/";
	console.log(localStorage.getItem("user"));
}
const add_to_cart = () => {
	var pageNameId = document.getElementById("page_name_id");
	var addToCart = document.getElementById("add_to_cart");
	var addToCartBtn = document.getElementsByClassName("add_to_cart_btn")[0];
	var removedId = document.getElementById("removed_id");
	//var form1Id = document.getElementById("form1_id");
	//var manageCartId = document.getElementById("manage_cart_id");
	//var productAdded = document.getElementById("product_added_id");
	//console.log("method:"+form1Id.getAttribute("method"));
	if (pageNameId.value === "yes" || removedId != null) {
		/*swal({
			title: "Good job!",
			text: "You have registered successfully!",
			icon: "success",
			button: "OK!",
		});*/

		addToCartBtn.disabled = true;


		//manageCartId.value = "submit";

		addToCart.click();
	}

	else {
		swal({


			title: "Not Logged In !!!",
			text: "Please login to use this feature",
			icon: "error",
			button: "Try Again!",
		});
	}
}
const test = (event) => {
	event.preventDefault();
	/*//var manageCartId = document.getElementById("manage_cart_id");
	var addToCartBtn = document.getElementsByClassName("add_to_cart_btn")[0];
	var productAdded = document.getElementById("product_added_id");
	console.log("pro:"+productAdded);
	//console.log(manageCartId.value);
	if (productAdded === null) {
		console.log("pro1:"+productAdded);
		addToCartBtn.disabled = true;
		return true;
	}
	else
		return false;*/
	//return false;
	/*if (manageCartId.value === "submit") {
		addToCartBtn.disabled = true;

		return true;
	}

	else {
		return false;
	}*/

}
const remove_item = (el) => {
	var element = el;
	var removeItemBtn = document.getElementsByClassName("remove_item_btn");
	var removeItemForm = document.getElementsByClassName("remove_item_form");
	console.log(removeItemBtn.lenght + ":" + removeItemForm.length);
	for (let i = 0; i < removeItemBtn.length; i++) {
		if (removeItemBtn[i] === element) {
			removeItemForm[i].click();
		}
	}
}
const cart_open = () => {
	//var cart = document.getElementsByClassName("cart")[0];
	console.log("cart open");
	//var manageCartId = document.getElementById("manage_cart_id");
	var cartTopDiv = document.getElementsByClassName("cart_top_div")[0];
	if (cartTopDiv.classList.contains("animation4"))
		cartTopDiv.classList.remove("animation4");
	cartTopDiv.classList.add("animation3");
	//manageCartId.value = "show";
	//console.log(manageCartId.value);
	localStorage.setItem("cart_status", "opened");
	//console.log(localStorage.getItem("cart"));
}
const cart_close = () => {
	//var cart = document.getElementsByClassName("cart")[0];
	//var manageCartId = document.getElementById("manage_cart_id");
	var cartTopDiv = document.getElementsByClassName("cart_top_div")[0];

	if (cartTopDiv.classList.contains("animation3"))
		cartTopDiv.classList.remove("animation3");
	cartTopDiv.classList.add("animation4");
	localStorage.removeItem("cart_status");
}
/*const search = () => {
	let query = $("#search-input").val(); 
	console.log(query);
};*/
const submit_seller = () => {
	var submitting = document.getElementsByClassName("submitting_details_top")[0];
	var loginOpt = document.getElementsByClassName("login_list")[0];
	var submit = document.getElementById("customer_login_btn10_submit");
	var searchArea1 = document.getElementsByClassName("search_area1")[0];
	/*swal({
							title: "Oops!",
							text: "Something went wrong!",
							icon: "error",
							button: "Try Again!",
						});*/
	searchArea1.style.display = "none";
	submitting.style.display = "block";


	loginOpt.style.display = "none";
	submit.click();

}
const paymentStart = () => {
	console.log("payment started");
	let amount = $("#amt_to_be_paid").val();
	console.log(amount);
	if (amount == "" || amount == null) {
		alert("amount is required !!!");
		return;
	}
	var products = [];
	$(".cart_item_id").each(function() {
		products.push($(this).val());
	});
	$.ajax(
		{
			url: "/customer/create_order",
			data: JSON.stringify({
				amount: amount, info: 'order_request', email: localStorage.getItem("user"),
				products: products
			}),
			contentType: "application/json",
			type: "POST",
			dataType: "json",
			success: function(response) {
				console.log('Response : ' + response);


				if (response.status == "created") {
					console.log("inside created")
					var options = {
						key: "rzp_test_y84XqHSv5nPYer", // Enter the Key ID generated from the Dashboard
						amount: response.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
						currency: "INR",
						name: "Easy Query",
						description: "Easy Query Donation",
						//image: "D:\\sts\\sts workspace\\smartcontactmanager-1\\src\\main\\resources\\static\\image\\banner1.jpg",
						order_id: response.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
						handler: function(response) {
							console.log(response.razorpay_payment_id);
							console.log(response.razorpay_order_id);
							console.log(response.razorpay_signature);
							updatePaymentOnServer(response.razorpay_payment_id, response.razorpay_order_id, 'paid', localStorage.getItem("user"));

						},
						"prefill": {
							"name": "Kumar Siddharth",
							"email": "budhakumar21@gmail.com",
							"contact": "9000090000"
						},
						"notes": {
							"address": "Easy Query"
						},
						"theme": {
							"color": "#3399cc"
						}
					};
					console.log(options.amount);
					var rzp = new Razorpay(options);

					rzp.on('payment.failed', function(response) {
						console.log('here 1');
						console.log(response.error.code);
						console.log(response.error.description);
						console.log(response.error.source);
						console.log(response.error.step);
						console.log(response.error.reason);
						console.log(response.error.metadata.order_id);
						console.log(response.error.metadata.payment_id);
						alert("payment failed")
						swal({
							title: "Oops!",
							text: "Something went wrong!",
							icon: "error",
							button: "Try Again!",
						});
					});
					rzp.open();

				}

			},
			error: function(error) {
				console.log(error);
				alert("something went wrong");
			}
		}
	);
};

function updatePaymentOnServer(razorpay_payment_id, razorpay_order_id, status, email) {
	$.ajax({
		url: "/customer/update_order",
		data: JSON.stringify({ razorpay_payment_id: razorpay_payment_id, razorpay_order_id: razorpay_order_id, status: status, email: email }),
		contentType: "application/json",
		type: "POST",
		dataType: "json",
		success: function(response) {
			swal({
				title: "Good job!",
				text: "Your payment is successfull!",
				icon: "success",
				button: "OK!",
			});
		},
		error: function(error) {
			swal({
				title: "Oops!",
				text: "your payment is successfull but something went wrong on server! we will contact you later",
				icon: "error",
				button: "Try Again!",
			});
		}
	})
}
const search = () => {
	const mq = window.matchMedia("(max-width:600px)");
	let query = $("#in_search_item").val();
	if (query === "") {
		//$(".search_result").html("");

		$(".search_result").addClass("no_display_important");
		/*$(".search_result").each(function(){
			$(this).html("<h5>hello</h5>");
		});*/
		if (mq.matches) {
			$(".search_item").css("height", "120px");
		}
		else {
			$(".search_item").css("height", "100px");
		}

	}
	else {
		$(".search_result").removeClass("no_display_important");
		$(".search_item").css("height", "auto");
		let url = `http://localhost:8080/item/search/${query}`;
		fetch(url)
			.then((response) => {
				return response.json();
			}).then((data) => {
				//data = JSON.parse(data);
				let text = `<ul class="list-group list-group-flush">`;
				data.forEach(product => {
					text += `<a style='cursor:pointer;' class='searched_item'>
					<li class="list-group-item bg-red searched_item_name" onclick='click_item(this)'>${product.p_name}</li></a>
					<input style='display:none;' type='hidden' class='product_id2' value=${product.p_id}></input>`;
					/*text+=`<form action='/item/show_product'}">
									<span class="pro_name" onclick="submit_pro(this)">${product.p_name}</span>
									<input type="hidden" name="page" value="index" />
									<input type="hidden" name="id" value="${product.p_id}" />
									<button class="pro_submit" type="submit" style="display: none;"></button>
								</form>`*/
				});
				text += `</ul>`;
				$(".search_result").html(text);
				$(".search_result").show();
			})
	}
}
const click_item = (el) => {
	var element = el;
	const mq = window.matchMedia("(max-width:600px)");
	if (mq.matches) {
		$(".search_item").css("height", "120px");
	}
	else {
		$(".search_item").css("height", "100px");
	}

	$(".searched_item_name").each((i, item) => {
		if (element === item) {
			console.log(i, "matched");
			$("#in_search_item").val($(item).text());
			//$(".search_result").html("");

			$("#product_id3").val($(".product_id2").eq(i).val());
			$(".search_result").html("");
			//console.log($(".product_id2").eq(i).val())
		}

	});
	/*console.log($(".search_result input").length);
	$(".product_id2").each((item)=>{
		console.log(item);
	});*/
	/*$(document).on("click", ".searched_item", function(ev) {
		//$(this).text("Liked!");
		$("#in_search_item").val($(this).text());
		$(".search_result").html("");
		i = ev.target;
		console.log(i);
		$(".searched_item").each((j, item) => {
		console.log("item");
		console.log(i);
		if (item === i)
			console.log(j);
	})

		$("#product_id3").val($(".product_id2").val());
	});*/
	//console.log(i);
	/*$(".searched_item").each((j, item) => {
		console.log(item);
		console.log(i);
		if (item === i)
			console.log(j);
	})*/
	/*var element = el;

	$(".searched_item").each((i, item) => {
		$(item).click(() => {
			if (item === element) {
				$("#in_search_item").val(item.text());
				$(item).html("");
				console.log(i);
				$("#product_id3").val($(".product_id2").eq(i).val());
			}
		});
	});*/
	/*$(".searched_item").on('click',((e) => {
		var target = $(e.target);
		console.log(target[0]);
		$(".searched_item").each((i,item)=>{
			console.log(item);
			if(item === target[0]){
				console.log(i);
			}
		});*/
	/*$(item).click((e) => {
		var target = $(e.target);
		console.log(target);
		if (target.is(item)) {
			$("#in_search_item").val($(item).text());
			$(item).html("");
			console.log(i);
			$("#product_id3").val($(".product_id2").eq(i).val());
			return;
		}

		//console.log($("#product_id3").val());
	});*/

}
const show_searched_item = () => {
	//$("#product_id3").val($("#in_search_item").val());
	$(".pro_submit")[0].click();
}
const get_distance = () => {
	fetch("https://api.ipify.org?format=json")
		.then(response => response.json())
		.then(data => {
			// Display the IP address on the screen
			/*document.getElementById("ip-address").textContent = data.ip;*/
			console.log(data.ip);
			const ip = data.ip;
			const accessKey = 'd5c77e11-409f-46d8-9f23-c1fc9de595cf';

			// Get the API result via jQuery.ajax
			$.ajax({
				url: 'https://apiip.net/api/check?ip=' + ip + '&accessKey=' + accessKey,
				success: function(result) {
					// Output the "code" value inside "currency" object
					console.log(result);
					console.log(result.latitude);
					calc_lat_long();
				}
			});
		})
		.catch(error => {
			console.error("Error fetching IP address:", error);
		});
}
const calc_lat_long = () => {
	/*var requestOptions = {
		method: 'GET',
	};*/
	const address = "Paschim Vihar,New Delhi-110063";
	fetch("https://geocode.maps.co/search?q=Paschim Vihar,New Delhi-110063&api_key=66c8951a139fa489190363nzm90f672")
		.then(response => console.log(response))
		//
		.catch(error => console.log('error', error));
	/*var geocoder = new google.maps.Geocoder();
	var address = "new york";

	geocoder.geocode({ 'address': address }, function(results, status) {

		if (status == google.maps.GeocoderStatus.OK) {
			var latitude = results[0].geometry.location.lat();
			var longitude = results[0].geometry.location.lng();
			alert(latitude);
		}
	});*/
}

const otp_to_mail = () =>{
	$('.search_area1').each(function(){
		if($(this).html().includes('Sending OTP')){
			$(this).show();

		}
		else{
			$(this).hide();
		}

	});
	$(".otp_to_mail_btn").click();
};
document.addEventListener("DOMContentLoaded", function() {

	displaySellerRegistration();



});
/*end*/
