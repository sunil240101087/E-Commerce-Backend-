package com.younesghu.Ecommerce.inventory;

import com.younesghu.Ecommerce.inventory.dto.UpdateProductInventoryRequest;
import com.younesghu.Ecommerce.inventory.exception.InventoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> listInventories() {
        return inventoryRepository.findAllwithProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getInventoryByProductId(UUID productId) {
        return inventoryRepository.findInventoryByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException(productId));
    }

    @Override
    public Inventory updateProductInventory(UUID productId, UpdateProductInventoryRequest request) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.changeAvailableQuantity(request.getAvailableQuantity());
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory reserveProduct(UUID productId, int quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.reserve(quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory releaseProduct(UUID productId, int quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.release(quantity);
        return inventoryRepository.save((inventory));
    }
}
