package br.com.erp.product.dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateDTO(

                String eanGtin,
                
                String sku,

                @NotBlank(message = "Nome do produto é obrigatório.") String name,

                String category,

                String subcategory,

                String brand,

                @PositiveOrZero(message = " Preço de venda não pode ser negativo.") @NotNull(message = " Preço do produto é obrigatório.") BigDecimal salePrice,

                @NotNull(message = "O preço de custo é obrigatório.") @PositiveOrZero(message = "O preço de custo não pode ser negativo.") BigDecimal costPrice,

                @NotNull(message = "O estoque atual é obrigatório.") @PositiveOrZero(message = "O estoque não pode ser negativo.") Integer currentStock,
                String unitOfMeasure,

                @NotNull(message = "O status (ativo/inativo) é obrigatório.") Boolean active

) {

}
