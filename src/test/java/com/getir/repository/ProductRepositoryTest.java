package com.getir.repository;

import com.getir.document.Product;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class ProductRepositoryTest {

  @BeforeEach
  void setUp() {
    productRepository.deleteAll().block();
  }

  @Autowired
  ProductRepository productRepository;

  @Test
  void shouldSaveProduct() {
    Product product = new Product();
    product.setDescription("description");
    product.setName("name");
    product.setPrice(200.0);
    product.setId(UUID.randomUUID());
    productRepository.save(product).block();

    StepVerifier.create(productRepository.findById(product.getId()))
        .expectSubscription()
        .expectNextMatches(foundProduct -> null != foundProduct.getId())
        .verifyComplete();
  }

  @Test
  void getAllProducts() {
    Product product = new Product();
    product.setDescription("description");
    product.setName("name");
    product.setPrice(200.0);
    product.setId(UUID.randomUUID());

    productRepository.save(product).block();

    StepVerifier.create(productRepository.findAll())
        .expectSubscription()
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  void getProductById() {
    UUID id = UUID.randomUUID();
    Product product = new Product();
    product.setDescription("description");
    product.setName("name");
    product.setPrice(200.0);
    product.setId(id);
    productRepository.save(product).block();

    StepVerifier.create(productRepository.findById(id))
        .expectSubscription()
        .expectNextMatches(foundProduct -> foundProduct.getName().equals("name"))
        .verifyComplete();
  }

  @Test
  void shouldUpdateProduct() {
    Product product = new Product();
    product.setDescription("description");
    product.setName("Ginger");
    product.setPrice(200.0);
    product.setId(UUID.randomUUID());

    Mono<Product> productMono = productRepository.save(product).map(product1 -> {
      product1.setName("Tea");
      return product1;
    }).flatMap(product1 -> productRepository.save(product1));

    StepVerifier.create(productMono)
        .expectSubscription()
        .expectNextMatches(product1 -> product1.getName().equals("Tea"))
        .verifyComplete();
  }

  @Test
  void shouldDeleteItemsById() {
    Product product = new Product();
    product.setDescription("description");
    product.setName("Ginger");
    product.setPrice(200.0);
    product.setId(UUID.randomUUID());

    Mono<Void> voidMono = productRepository.save(product)
        .map(product1 -> product.getId())
        .flatMap(id -> productRepository.deleteById(id));

    StepVerifier.create(voidMono)
        .expectSubscription()
        .verifyComplete();
  }
}