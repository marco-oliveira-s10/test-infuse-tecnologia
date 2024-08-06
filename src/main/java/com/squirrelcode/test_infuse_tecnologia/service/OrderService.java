package com.squirrelcode.test_infuse_tecnologia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squirrelcode.test_infuse_tecnologia.model.Order;
import com.squirrelcode.test_infuse_tecnologia.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public List<Order> createAll(List<Order> orders) {
		
		for (Order order : orders) {
		
			if (order.getRegistrationDate() == null) {
				order.setRegistrationDate(LocalDate.now());
			}
			if (order.getQuantity() == 0) {
				order.setQuantity(1);
			}
			
			order.calculateTotalValue();
		}
		return orderRepository.saveAll(orders);
	}

	public Order getOrderByControlNumber(String controlNumber) {
		return orderRepository.findByControlNumber(controlNumber);
	}

	public Page<Order> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}
}
