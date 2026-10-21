package com.younesghu.Ecommerce.inventory;

import com.younesghu.Ecommerce.inventory.dto.InventoryResponse;
import com.younesghu.Ecommerce.inventory.dto.UpdateProductInventoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> listInventories() {
        List<InventoryResponse> inventories = inventoryService.listInventories().stream().map(inventoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(inventories);
    }

    @GetMapping(path = "/product/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable("productId") UUID productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventoryMapper.toDto(inventory));
    }

    @PutMapping(path = "/product/{id}")
    public ResponseEntity<InventoryResponse> updateProductInventory(
        @PathVariable UUID id,
        @Valid @RequestBody UpdateProductInventoryRequest updateProductInventoryRequest
    ) {
        Inventory updatedInventory = inventoryService.updateProductInventory(id, updateProductInventoryRequest);
        return ResponseEntity.ok(inventoryMapper.toDto(updatedInventory));
    }

}
