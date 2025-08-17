package br.com.erp.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import br.com.erp.product.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{

    boolean existsByEanGtin(String eanGtin);

}
