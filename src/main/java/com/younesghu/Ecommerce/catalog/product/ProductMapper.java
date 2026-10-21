package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.product.dto.CreateProductRequest;
import com.younesghu.Ecommerce.catalog.product.dto.ProductResponse;
import com.younesghu.Ecommerce.catalog.product.dto.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryTitle", source = "category.title")
    ProductResponse toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "description", ignore = true)
    Product toEntity(CreateProductRequest createProductRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "description", ignore = true)
    void updateEntityFromRequest(UpdateProductRequest updateProductRequest, @MappingTarget Product product);

}
