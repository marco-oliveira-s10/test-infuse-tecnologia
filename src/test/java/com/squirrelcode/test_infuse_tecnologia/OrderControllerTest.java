package com.squirrelcode.test_infuse_tecnologia;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squirrelcode.test_infuse_tecnologia.controller.OrderController;
import com.squirrelcode.test_infuse_tecnologia.model.Order;
import com.squirrelcode.test_infuse_tecnologia.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@MockBean
	private OrderService orderService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	public void testCreateOrders_ValidOrders() throws Exception {

		List<Order> orders = Arrays.asList(new Order("ORD1", "Product A", 99.99, 1, 456),
				new Order("ORD2", "Product B", 59.99, 2, 123));
		when(orderService.createAll(orders)).thenReturn(orders);
		String jsonRequest = objectMapper.writeValueAsString(orders);
		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreateOrders_ExceedingLimit() throws Exception {
		List<Order> orders = Arrays.asList(new Order("ORD1", "Product A", 99.99, 1, 456),
				new Order("ORD2", "Product B", 59.99, 2, 123), new Order("ORD3", "Product C", 79.99, 3, 789),
				new Order("ORD4", "Product D", 89.99, 4, 101), new Order("ORD5", "Product E", 69.99, 5, 112),
				new Order("ORD6", "Product F", 109.99, 6, 131), new Order("ORD7", "Product G", 119.99, 7, 415),
				new Order("ORD8", "Product H", 129.99, 8, 161), new Order("ORD9", "Product I", 139.99, 9, 171),
				new Order("ORD10", "Product J", 149.99, 10, 181), new Order("ORD11", "Product K", 159.99, 11, 191));

		String jsonRequest = objectMapper.writeValueAsString(orders);

		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("The number of orders cannot exceed 10."));
	}
}
