package com.younesghu.Ecommerce.inventory;

import com.younesghu.Ecommerce.inventory.dto.UpdateProductInventoryRequest;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<Inventory> listInventories();

    Inventory getInventoryByProductId(UUID productId);

    Inventory updateProductInventory(UUID productId, UpdateProductInventoryRequest request);

    Inventory reserveProduct(UUID productId, int quantity);

    Inventory releaseProduct(UUID productId, int quantity);
}
