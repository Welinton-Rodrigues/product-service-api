package br.com.erp.product; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@SpringBootApplication
public class ProductServiceApplication {

    /**
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}