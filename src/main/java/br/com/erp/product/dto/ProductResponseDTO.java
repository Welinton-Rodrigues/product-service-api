package br.com.erp.product.dto;

import java.math.BigDecimal;
import java.time.Instant;



public record ProductResponseDTO(

        Long id,
        String eanGtin,
        String name,
        String category,
        String subCategory,
        String brand,
        BigDecimal salePrice,
        Integer currentStock,
        String unitOfMeasure,
        Boolean active,
        Instant createdAt,
        Instant updatedAt

) {

}
