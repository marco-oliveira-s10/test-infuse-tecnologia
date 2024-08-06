package com.squirrelcode.test_infuse_tecnologia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squirrelcode.test_infuse_tecnologia.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findByControlNumber(String controlNumber);
}