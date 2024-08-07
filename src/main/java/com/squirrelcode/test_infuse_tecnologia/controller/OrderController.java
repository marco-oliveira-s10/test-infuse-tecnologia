package com.squirrelcode.test_infuse_tecnologia.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squirrelcode.test_infuse_tecnologia.model.Order;
import com.squirrelcode.test_infuse_tecnologia.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> createOrders(@RequestBody List<Order> orders) {
		if (orders.size() > 10) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The number of orders cannot exceed 10.");
		}

		List<Order> createdOrders = orderService.createAll(orders);
		List<EntityModel<Order>> orderModels = createdOrders.stream().map(order -> EntityModel.of(order)
				.add(WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrder(order.getControlNumber()))
						.withSelfRel()))
				.toList();

		return new ResponseEntity<>(orderModels, HttpStatus.CREATED);
	}

	@GetMapping
	public CollectionModel<EntityModel<Order>> getAllOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String controlNumber,
			@RequestParam(required = false) LocalDate registrationDate,
			@RequestParam(required = false) String productName, @RequestParam(required = false) Double unitPrice,
			@RequestParam(required = false) Integer quantity, @RequestParam(required = false) Integer customerCode,
			@RequestParam(required = false) Double totalPriceWithDiscount,
			@RequestParam(required = false) Double totalPriceWithoutDiscount) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orderPage;

		if (controlNumber != null || registrationDate != null || productName != null || unitPrice != null
				|| quantity != null || customerCode != null || totalPriceWithDiscount != null
				|| totalPriceWithoutDiscount != null) {
			orderPage = orderService.getAllOrders(pageable, controlNumber, registrationDate, productName, unitPrice,
					quantity, customerCode, totalPriceWithDiscount, totalPriceWithoutDiscount);
		} else {
			orderPage = orderService.getAllOrders(pageable);
		}

		return CollectionModel.of(
				orderPage.getContent().stream()
						.map(order -> EntityModel.of(order,
								WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
										.getOrder(order.getControlNumber())).withSelfRel()))
						.toList(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders(page, size,
						productName, registrationDate, productName, totalPriceWithoutDiscount, customerCode,
						customerCode, totalPriceWithoutDiscount, totalPriceWithoutDiscount)).withSelfRel());
	}

	@GetMapping("/{controlNumber}")
	public ResponseEntity<Order> getOrder(@PathVariable String controlNumber) {
		Order order = orderService.findByControlNumber(controlNumber);
		if (order != null) {
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
