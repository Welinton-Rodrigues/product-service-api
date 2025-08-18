package br.com.erp.product.controller;

import br.com.erp.product.dto.ProductCreateDTO;
import br.com.erp.product.dto.ProductResponseDTO;
import br.com.erp.product.dto.ProductUpdateDTO;
import br.com.erp.product.dto.StockUpdateDTO;
import br.com.erp.product.exception.ErrorResponse;
import br.com.erp.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Gestão de Produtos", description = "Endpoints para o CRUD de Produtos")
public class ProductController {

        private final ProductService productService;

        @Operation(summary = "Cria um novo produto", description = "Cadastra um novo produto no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO dto) {
                ProductResponseDTO newProduct = productService.createProduct(dto);
                return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        }

        @Operation(summary = "Busca um produto por ID", description = "Retorna os detalhes de um produto específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<ProductResponseDTO> getProductById(
                        @Parameter(description = "ID do produto a ser buscado", required = true, example = "1") @PathVariable Long id) {
                ProductResponseDTO product = productService.getProductById(id);
                return ResponseEntity.ok(product);
        }

        @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista paginada de produtos, com filtros opcionais.")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso") })
@GetMapping
public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
        @Parameter(description = "Filtrar por nome do produto (busca parcial)")
        @RequestParam(required = false) String name,
        
        @Parameter(description = "Filtrar por categoria do produto")
        @RequestParam(required = false) String category,

        @Parameter(description = "Filtrar apenas produtos com estoque zerado")
        @RequestParam(required = false) Boolean noStock,

        @ParameterObject Pageable pageable) {
            
    Page<ProductResponseDTO> products = productService.getAllProducts(name, category, noStock, pageable);
    return ResponseEntity.ok(products);
}

        @Operation(summary = "Atualiza um produto existente", description = "Atualiza todos os dados de um produto com base no seu ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<ProductResponseDTO> updateProduct(
                        @Parameter(description = "ID do produto a ser atualizado", required = true, example = "1") @PathVariable Long id,
                        @RequestBody ProductUpdateDTO dto) {
                ProductResponseDTO updatedProduct = productService.updateProduct(id, dto);
                return ResponseEntity.ok(updatedProduct);
        }

        @Operation(summary = "Adiciona estoque a um produto", description = "Realiza uma entrada de estoque para um produto específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                        @ApiResponse(responseCode = "400", description = "Quantidade inválida (ex: negativa)")
        })
        @PatchMapping("/{id}/add-stock")
        public ResponseEntity<ProductResponseDTO> addStock(
                        @Parameter(description = "ID do produto", required = true, example = "1") @PathVariable Long id,
                        @RequestBody StockUpdateDTO dto) {
                ProductResponseDTO updatedProduct = productService.addStock(id, dto);
                return ResponseEntity.ok(updatedProduct);
        }

        @Operation(summary = "Remove estoque de um produto", description = "Realiza uma baixa de estoque para um produto específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                        @ApiResponse(responseCode = "400", description = "Quantidade inválida ou estoque insuficiente")
        })
        @PatchMapping("/{id}/remove-stock")
        public ResponseEntity<ProductResponseDTO> removeStock(
                        @Parameter(description = "ID do produto", required = true, example = "1") @PathVariable Long id,
                        @RequestBody StockUpdateDTO dto) {
                ProductResponseDTO updatedProduct = productService.removeStock(id, dto);
                return ResponseEntity.ok(updatedProduct);
        }

        @Operation(summary = "Deleta um produto", description = "Remove um produto permanentemente do sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(
                        @Parameter(description = "ID do produto a ser deletado", required = true, example = "1") @PathVariable Long id) {
                productService.deleteProduct(id);
                return ResponseEntity.noContent().build();
        }
}