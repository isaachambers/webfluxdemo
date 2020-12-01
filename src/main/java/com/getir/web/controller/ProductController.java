package com.getir.web.controller;

import com.getir.document.Product;
import com.getir.service.ProductService;
import com.getir.web.controller.request.CreateProductRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/v1/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("")
  public Flux<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping("")
  public Mono<ResponseEntity<Product>> createProduct(@RequestBody CreateProductRequest request) {
    return productService.createProduct(new Product(UUID.randomUUID(), request.getName(), request.getDescription(), request.getPrice()))
        .map(product -> new ResponseEntity<>(product, HttpStatus.CREATED));
  }

  @DeleteMapping("/{uuid}")
  public Mono<Void> deleteById(@PathVariable UUID uuid) {
    return productService.deleteById(uuid);
  }

  @GetMapping("/{uuid}")
  public Mono<ResponseEntity<Product>> getProductById(@PathVariable UUID uuid) {
    return productService.getProductById(uuid)
        .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{uuid}")
  public Mono<ResponseEntity<Product>> createProduct(@PathVariable UUID uuid, @RequestBody CreateProductRequest request) {
    return productService.updateProduct(uuid, request)
        .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}