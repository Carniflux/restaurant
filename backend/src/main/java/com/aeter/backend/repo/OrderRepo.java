package com.aeter.backend.repo;

import com.aeter.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepo extends JpaRepository<Order, Long> {


    @Query(value = "insert into orders (name, quantity) values (:name, :quantity)", nativeQuery = true )
    Order insertOrder(@Param("name") String name, @Param("quantity") Integer quantity);
}
