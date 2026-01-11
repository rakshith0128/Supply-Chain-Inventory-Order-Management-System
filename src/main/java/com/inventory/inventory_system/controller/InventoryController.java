package com.inventory.inventory_system.controller;

import com.inventory.inventory_system.entity.Inventory;
import com.inventory.inventory_system.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public Inventory addStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam int quantity) {

        return inventoryService.addStock(productId, warehouseId, quantity);
    }

    @PostMapping("/remove")
    public Inventory removeStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam int quantity) {

        return inventoryService.removeStock(productId, warehouseId, quantity);
    }
}
