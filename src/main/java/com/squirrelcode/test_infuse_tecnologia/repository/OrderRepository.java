package com.squirrelcode.test_infuse_tecnologia.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.squirrelcode.test_infuse_tecnologia.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findByControlNumber(String controlNumber);

	@Query("SELECT o FROM Order o WHERE " + "(:controlNumber IS NULL OR o.controlNumber = :controlNumber) AND "
			+ "(:registrationDate IS NULL OR o.registrationDate = :registrationDate) AND "
			+ "(:productName IS NULL OR o.productName = :productName) AND "
			+ "(:unitPrice IS NULL OR o.unitPrice = :unitPrice) AND "
			+ "(:quantity IS NULL OR o.quantity = :quantity) AND "
			+ "(:customerCode IS NULL OR o.customerCode = :customerCode) AND "
			+ "(:totalPriceWithDiscount IS NULL OR o.totalPriceWithDiscount = :totalPriceWithDiscount) AND "
			+ "(:totalPriceWithoutDiscount IS NULL OR o.totalPriceWithoutDiscount = :totalPriceWithoutDiscount)")
	Page<Order> findAllWithFilters(Pageable pageable, @Param("controlNumber") String controlNumber,
			@Param("registrationDate") LocalDate registrationDate, @Param("productName") String productName,
			@Param("unitPrice") Double unitPrice, @Param("quantity") Integer quantity,
			@Param("customerCode") Integer customerCode, @Param("totalPriceWithDiscount") Double totalPriceWithDiscount,
			@Param("totalPriceWithoutDiscount") Double totalPriceWithoutDiscount);

	boolean existsByControlNumber(String controlNumber);

}
