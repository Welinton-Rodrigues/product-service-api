package br.com.erp.product.exception;
import java.time.Instant;


public record ErrorResponse(

Instant timeStamp,
Integer status ,
String error,
String message,
String path
) {
    
}