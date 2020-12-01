package com.getir.service;

import com.getir.document.Product;
import com.getir.repository.ProductRepository;
import com.getir.web.controller.request.CreateProductRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Flux<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Mono<Product> createProduct(Product product) {
    return productRepository.save(product);
  }

  public Mono<Void> deleteById(UUID uuid) {
    return productRepository.deleteById(uuid);
  }

  public Mono<Product> getProductById(UUID uuid) {
    return productRepository.findById(uuid);
  }

  public Mono<Product> updateProduct(UUID uuid, CreateProductRequest request) {
    return getProductById(uuid).flatMap(product -> {
      product.setName(request.getName());
      product.setDescription(request.getDescription());
      product.setPrice(request.getPrice());
      return productRepository.save(product);
    });
  }
}