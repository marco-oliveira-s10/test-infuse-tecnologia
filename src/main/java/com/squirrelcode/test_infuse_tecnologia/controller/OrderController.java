package com.squirrelcode.test_infuse_tecnologia.controller;

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
@RequestMapping("/orders")
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
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Order> orderPage = orderService.getAllOrders(pageable);

		return CollectionModel.of(
				orderPage.getContent().stream()
						.map(order -> EntityModel.of(order,
								WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
										.getOrder(order.getControlNumber())).withSelfRel()))
						.toList(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders(page, size))
						.withSelfRel());
	}

	@GetMapping("/{controlNumber}")
	public EntityModel<Order> getOrder(@PathVariable String controlNumber) {
		Order order = orderService.getOrderByControlNumber(controlNumber);
		return EntityModel.of(order,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrder(controlNumber))
						.withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders(0, 10))
						.withRel("orders"));
	}
}
