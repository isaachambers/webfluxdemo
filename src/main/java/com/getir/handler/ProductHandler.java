package com.getir.handler;

import com.getir.document.Product;
import com.getir.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

  private final ProductService productService;

  public ProductHandler(ProductService productService) {
    this.productService = productService;
  }

  public Mono<ServerResponse> getAllProducts() {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(productService.getAllProducts(), Product.class);
  }
}
