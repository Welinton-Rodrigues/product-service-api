package br.com.erp.product.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(unique = true)
    private String eanGtin;

    @Column(nullable = false)
    @NotBlank(message = "Nome do produto é obrigatório.")
    private String name;

    private String category;

    private String subcategory;

    private String brand;

    @Column(nullable = false, precision = 10, scale = 2)
    @PositiveOrZero(message = " Preço de venda não pode ser negativo.")
    @NotNull(message = " Preço do produto é obrigatório.")
    private BigDecimal salePrice;

    @NotNull(message = "O preço de custo é obrigatório.")
    @PositiveOrZero(message = "O preço de custo não pode ser negativo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;

    @NotNull(message = "O estoque atual é obrigatório.")
    @PositiveOrZero(message = "O estoque não pode ser negativo.")
    @Column(nullable = false)
    private Integer currentStock;

    private String unitOfMeasure;

    @NotNull(message = "O status (ativo/inativo) é obrigatório.")
    private boolean active;
    // --- Campos de Auditoria ---

    @CreationTimestamp // O Hibernate vai preencher com a data/hora de criação automaticamente
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp // O Hibernate vai preencher com a data/hora da última atualização
    @Column(nullable = false)
    private Instant updatedAt;

}
