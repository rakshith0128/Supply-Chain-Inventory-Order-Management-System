package com.inventory.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.inventory_system.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
}
