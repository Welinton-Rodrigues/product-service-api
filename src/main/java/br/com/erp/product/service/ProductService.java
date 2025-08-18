package br.com.erp.product.service;

import br.com.erp.product.dto.ProductCreateDTO;
import br.com.erp.product.dto.ProductResponseDTO;
import br.com.erp.product.dto.ProductUpdateDTO;
import br.com.erp.product.dto.StockUpdateDTO;
import br.com.erp.product.exception.ResourceNotFoundException;
import br.com.erp.product.model.Product;
import br.com.erp.product.repository.ProductRepository;
import br.com.erp.product.repository.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final  ProductRepository productRepository;


    public ProductResponseDTO createProduct(ProductCreateDTO dto) {
        if (dto.eanGtin() != null && !dto.eanGtin().isBlank()) {
            if (productRepository.existsByEanGtin(dto.eanGtin())) {
                throw new IllegalArgumentException("O código de barras EAN/GTIN '" + dto.eanGtin() + "' já está cadastrado.");
            }
        }

     Product newProduct = new Product();
    mapDtoToEntity(dto, newProduct);
    
    newProduct.setSku(generateSku());

    Product savedProduct = productRepository.save(newProduct);
    return convertToDto(savedProduct);
}

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = findProductById(id);
        return convertToDto(product);
    }

public Page<ProductResponseDTO> getAllProducts(String name, String category, Boolean noStock, Pageable pageable) {
    Specification<Product> spec = Specification.where(null);

    if (name != null && !name.isBlank()) {
        spec = spec.and(ProductSpecification.hasName(name));
    }
    if (category != null && !category.isBlank()) {
        spec = spec.and(ProductSpecification.hasCategory(category));
    }
    if (noStock != null && noStock) {
        spec = spec.and(ProductSpecification.hasNoStock());
    }

    Page<Product> productPage = productRepository.findAll(spec, pageable);
    
    return productPage.map(this::convertToDto);
}

    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO dto) {
        Product product = findProductById(id);
        mapDtoToEntity(dto, product);
        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    public ProductResponseDTO addStock(Long id, StockUpdateDTO dto) {
        if (dto.quantity() <= 0) {
            throw new IllegalArgumentException("A quantidade a ser adicionada deve ser positiva.");
        }
        Product product = findProductById(id);
        product.setCurrentStock(product.getCurrentStock() + dto.quantity());
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
    
    public ProductResponseDTO removeStock(Long id, StockUpdateDTO dto) {
        if (dto.quantity() <= 0) {
            throw new IllegalArgumentException("A quantidade a ser removida deve ser positiva.");
        }
        Product product = findProductById(id);
        if (product.getCurrentStock() < dto.quantity()) {
            throw new IllegalArgumentException("Estoque insuficiente. Estoque atual: " + product.getCurrentStock());
        }
        product.setCurrentStock(product.getCurrentStock() - dto.quantity());
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));
    }

    private String generateSku() {
    var lastProduct = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().findFirst();
    
    long nextId = lastProduct.map(product -> product.getId() + 1).orElse(1L);
    
    return "PROD-" + String.format("%06d", nextId);
}

    private ProductResponseDTO convertToDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getEanGtin(),
                product.getName(),
                product.getCategory(),
                product.getSubcategory(),
                product.getBrand(),
                product.getSalePrice(),
                product.getCurrentStock(),
                product.getUnitOfMeasure(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }

    private void mapDtoToEntity(ProductCreateDTO dto, Product product) {
        product.setEanGtin(dto.eanGtin());
        product.setSku(dto.sku());
        product.setName(dto.name());
        product.setCategory(dto.category());
        product.setSubcategory(dto.subcategory());
        product.setBrand(dto.brand());
        product.setSalePrice(dto.salePrice());
        product.setCostPrice(dto.costPrice());
        product.setCurrentStock(dto.currentStock());
        product.setUnitOfMeasure(dto.unitOfMeasure());
    }

    private void mapDtoToEntity(ProductUpdateDTO dto, Product product) {
        product.setSku(dto.sku());
        product.setEanGtin(dto.eanGtin());
        product.setName(dto.name());
        product.setCategory(dto.category());
        product.setSubcategory(dto.subcategory());
        product.setBrand(dto.brand());
        product.setSalePrice(dto.salePrice());
        product.setCostPrice(dto.costPrice());
        product.setUnitOfMeasure(dto.unitOfMeasure());
        product.setActive(dto.active());
    }
}