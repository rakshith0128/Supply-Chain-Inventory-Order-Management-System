package com.inventory.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.inventory_system.entity.Inventory;
import com.inventory.inventory_system.entity.Product;
import com.inventory.inventory_system.entity.Warehouse;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);
}
