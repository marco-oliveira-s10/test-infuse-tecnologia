package com.squirrelcode.test_infuse_tecnologia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.squirrelcode.test_infuse_tecnologia.model.Order;
import com.squirrelcode.test_infuse_tecnologia.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public class DuplicateOrderException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public DuplicateOrderException(String message) {
			super(message);
		}
	}

	public List<Order> createAll(List<Order> orders) {
		for (Order order : orders) {
			if (order.getRegistrationDate() == null) {
				order.setRegistrationDate(LocalDate.now());
			}
			if (order.getQuantity() == 0) {
				order.setQuantity(1);
			}
			order.calculateTotalValue();

			// Verificação de duplicatas
			if (orderRepository.existsByControlNumber(order.getControlNumber())) {
				throw new DuplicateOrderException(
						"Order with control number " + order.getControlNumber() + " already exists.");
			}
		}
		return orderRepository.saveAll(orders);
	}

	public Page<Order> getAllOrders(Pageable pageable, String controlNumber, LocalDate registrationDate,
			String productName, Double unitPrice, Integer quantity, Integer customerCode, Double totalPriceWithDiscount,
			Double totalPriceWithoutDiscount) {
		return orderRepository.findAllWithFilters(pageable, controlNumber, registrationDate, productName, unitPrice,
				quantity, customerCode, totalPriceWithDiscount, totalPriceWithoutDiscount);
	}

	public Page<Order> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	public Order findByControlNumber(String controlNumber) {
		return orderRepository.findByControlNumber(controlNumber);
	}
}
