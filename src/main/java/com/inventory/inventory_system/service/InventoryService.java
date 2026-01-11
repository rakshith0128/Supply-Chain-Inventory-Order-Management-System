package com.inventory.inventory_system.service;

import com.inventory.inventory_system.entity.Inventory;
import com.inventory.inventory_system.entity.Product;
import com.inventory.inventory_system.entity.Warehouse;
import com.inventory.inventory_system.repository.InventoryRepository;
import com.inventory.inventory_system.repository.ProductRepository;
import com.inventory.inventory_system.repository.WarehouseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Add stock for a product in a warehouse.
     * Creates inventory if it does not exist.
     */
    @Transactional
    public Inventory addStock(Long productId, Long warehouseId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));

        Inventory inventory = inventoryRepository
                .findByProductAndWarehouse(product, warehouse)
                .orElse(new Inventory(product, warehouse, 0));

        inventory.setQuantity(inventory.getQuantity() + quantity);

        return inventoryRepository.save(inventory);
    }

    /**
     * Remove stock (used during order processing).
     * Prevents negative inventory.
     */
    @Transactional
    public Inventory removeStock(Long productId, Long warehouseId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));

        Inventory inventory = inventoryRepository
                .findByProductAndWarehouse(product, warehouse)
                .orElseThrow(() -> new IllegalStateException("Inventory record not found"));

        if (inventory.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);

        return inventoryRepository.save(inventory);
    }
}
