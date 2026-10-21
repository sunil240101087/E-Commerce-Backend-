package com.younesghu.Ecommerce.catalog.category;

import com.younesghu.Ecommerce.catalog.category.dto.CategoryResponse;
import com.younesghu.Ecommerce.catalog.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "productCount", source = "products", qualifiedByName = "calculateProductCount")
    CategoryResponse toDto(Category category);

    @Named("calculateProductCount")
    default long calculateProductCount(List<Product> products) {
        if (products == null) {
            return 0;
        }
        return products.size();
    }
}
