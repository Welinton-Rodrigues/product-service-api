package br.com.erp.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ProductUpdateDTO(
    
    String eanGtin,

    @NotBlank(message = "O nome do produto é obrigatório.")
    String name,

    String category,

    String subcategory,

    String brand,

    @NotNull(message = "O preço de venda é obrigatório.")
    @PositiveOrZero(message = "O preço de venda não pode ser negativo.")
    BigDecimal salePrice,

    @NotNull(message = "O preço de custo é obrigatório.")
    @PositiveOrZero(message = "O preço de custo não pode ser negativo.")
    BigDecimal costPrice,

    String unitOfMeasure,
    
    // Podemos adicionar o status aqui, para permitir ativar/inativar na tela de edição
    @NotNull(message = "O status (ativo/inativo) é obrigatório.")
    Boolean active 
) {}