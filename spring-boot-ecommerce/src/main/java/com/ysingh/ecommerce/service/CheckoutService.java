package com.ysingh.ecommerce.service;

import com.ysingh.ecommerce.dto.Purchase;
import com.ysingh.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

	PurchaseResponse placeOrder(Purchase purchase);
}
