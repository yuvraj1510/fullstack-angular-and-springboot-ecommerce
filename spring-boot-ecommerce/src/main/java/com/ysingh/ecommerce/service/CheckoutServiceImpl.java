package com.ysingh.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.ysingh.ecommerce.dao.CustomerRepository;
import com.ysingh.ecommerce.dto.PaymentInfo;
import com.ysingh.ecommerce.dto.Purchase;
import com.ysingh.ecommerce.dto.PurchaseResponse;
import com.ysingh.ecommerce.entity.Customer;
import com.ysingh.ecommerce.entity.Order;
import com.ysingh.ecommerce.entity.OrderItem;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository,
	                           @Value("${stripe.key.secret}") String secretKey) {
		this.customerRepository = customerRepository;
		Stripe.apiKey = secretKey;
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
		
		Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());
		
		if(customerFromDB != null) {
		    customer = customerFromDB;
		}
		
		customer.add(order);
		
		customerRepository.save(customer);
		
		PurchaseResponse purchaseResponse = new PurchaseResponse();
		purchaseResponse.setOrderTrackingNumber(orderTrackingNumber);
		
		return purchaseResponse;
	}

	private String generateOrderTrackingNumber() {
		return UUID.randomUUID().toString();
	}

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "ysingh E-Commerce Purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        
        return PaymentIntent.create(params);
    }

}
