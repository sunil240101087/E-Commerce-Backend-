package com.younesghu.Ecommerce.inventory;

import com.younesghu.Ecommerce.inventory.dto.InventoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productTitle", source = "product.title")
    InventoryResponse toDto(Inventory inventory);
}
