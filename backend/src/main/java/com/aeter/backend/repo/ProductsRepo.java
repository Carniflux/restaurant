package com.aeter.backend.repo;

import com.aeter.backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductsRepo extends JpaRepository<Products, Long> {
    @Modifying
    @Query("update Products p set p.quantity = p.quantity - :updateQuantity where p.name = :name")
    void update(@Param("updateQuantity") Integer updateQuantity, @Param("name") String name);

    @Modifying
    @Query("delete from Products p where p.name = :name")
    void deleteByName(@Param("name") String name);
}
