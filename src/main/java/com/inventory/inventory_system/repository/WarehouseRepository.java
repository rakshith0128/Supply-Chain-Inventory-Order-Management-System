package com.inventory.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.inventory_system.entity.Warehouse;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByName(String name);
}
