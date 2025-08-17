package br.com.erp.product.repository.specification;

import br.com.erp.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    // filtrar por nome (usando LIKE para busca parcial)
  public static Specification<Product> hasName(String name) {
    return (root, query, criteriaBuilder) ->
            // Adicionamos .trim() para remover espaços extras do termo de busca
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), 
                "%" + name.toLowerCase().trim() + "%"
            );
}

    //  para filtrar por categoria (busca exata)
    public static Specification<Product> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }
    
    //  para filtrar por estoque zerado
    public static Specification<Product> hasNoStock() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("currentStock"), 0);
    }
    
    // Podemos adicionar outros filtros aqui no futuro (ex: por marca, por preço, etc.)
}