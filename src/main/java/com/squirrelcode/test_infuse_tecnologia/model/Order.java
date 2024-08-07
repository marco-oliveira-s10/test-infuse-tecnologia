package com.squirrelcode.test_infuse_tecnologia.model;

import java.time.LocalDate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_order")
@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String controlNumber;

	private LocalDate registrationDate;
	private String productName;
	private double unitPrice;
	private int quantity;
	private int customerCode;

	@Column(name = "total_price_with_discount")
	private double totalPriceWithDiscount; // Total with discount

	@Column(name = "total_price_without_discount")
	private double totalPriceWithoutDiscount; // Total without discount

	@Transient
	private double discountedTotalValue;

	public void calculateTotalValue() {

		this.totalPriceWithoutDiscount = this.unitPrice * this.quantity;

		if (this.quantity >= 10) {

			this.totalPriceWithDiscount = this.totalPriceWithoutDiscount * 0.9; // 10% discount

		} else if (this.quantity > 5) {

			this.totalPriceWithDiscount = this.totalPriceWithoutDiscount * 0.95; // 5% discount

		} else {

			this.totalPriceWithDiscount = this.totalPriceWithoutDiscount;
		}

		this.discountedTotalValue = this.totalPriceWithDiscount;
	}

	// Construtor com todos os parâmetros
	public Order(String controlNumber, String productName, double unitPrice, int quantity, int customerCode) {
		this.controlNumber = controlNumber;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.customerCode = customerCode;
	}
}
