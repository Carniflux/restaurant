package com.aeter.backend.repo;

import com.aeter.backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<Products, Long> {
}
