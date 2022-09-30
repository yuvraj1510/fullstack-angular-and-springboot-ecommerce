package com.ysingh.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysingh.ecommerce.dao.CustomerRepository;
import com.ysingh.ecommerce.dto.Purchase;
import com.ysingh.ecommerce.dto.PurchaseResponse;
import com.ysingh.ecommerce.entity.Customer;
import com.ysingh.ecommerce.entity.Order;
import com.ysingh.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		Order order = purchase.getOrder();
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		Set<OrderItem> orderItems = purchase.getOrderItems();
		
		orderItems.stream().forEach(item -> order.add(item));
		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());
		
		Customer customer = purchase.getCustomer();
		customer.add(order);
		
		customerRepository.save(customer);
		
		PurchaseResponse purchaseResponse = new PurchaseResponse();
		purchaseResponse.setOrderTrackingNumber(orderTrackingNumber);
		
		return purchaseResponse;
	}

	private String generateOrderTrackingNumber() {
		return UUID.randomUUID().toString();
	}

}
