package com.ysingh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysingh.ecommerce.dto.Purchase;
import com.ysingh.ecommerce.dto.PurchaseResponse;
import com.ysingh.ecommerce.service.CheckoutService;

@CrossOrigin
@RestController
@RequestMapping("api/checkout")
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("/purchase")
	public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
		return checkoutService.placeOrder(purchase);
	}
}
