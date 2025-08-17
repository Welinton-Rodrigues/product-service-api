package br.com.erp.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockUpdateDTO(

@NotNull(message = "A quantidade é obrigatória.")
@Positive(message = "A quantidade para movimentação de estoque deve ser maior que zero.")
Integer quantity

) {
    
}
