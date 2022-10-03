package com.ysingh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.ysingh.ecommerce.dto.PaymentInfo;
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
	
	@PostMapping("/payment-intent")
	public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
	    
	    PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
	    
	    String paymentStr = paymentIntent.toJson();
	    
	    return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	    
	}
}
