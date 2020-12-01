package com.getir.web.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;


import com.getir.IntegrationTest;
import com.getir.document.Product;
import com.getir.repository.ProductRepository;
import com.getir.web.controller.request.CreateProductRequest;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class ProductControllerTest extends IntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    productRepository.deleteAll().block();
  }

  @Test
  void getAllProducts() {
    productRepository
        .saveAll(Arrays.asList(new Product(UUID.randomUUID(), "Salt", "Normal Salt", 12.45),
            new Product(UUID.randomUUID(), "Tomato", "Ripe Tomatoes", 5.00),
            new Product(UUID.randomUUID(), "Coffee", "Coffee Beans", 45.3),
            new Product(UUID.randomUUID(), "Ketchup", "Hot and Sweet", 25.00)))
        .blockLast();


    webTestClient.get().uri("/v1/product")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Product.class).consumeWith(listEntityExchangeResult ->
        assertThat(listEntityExchangeResult.getResponseBody())
            .asList()
            .extracting("name", "description", "price")
            .containsExactlyInAnyOrder(tuple("Salt", "Normal Salt", 12.45),
                tuple("Tomato", "Ripe Tomatoes", 5.00),
                tuple("Coffee", "Coffee Beans", 45.3),
                tuple("Ketchup", "Hot and Sweet", 25.00)));
  }

  @Test
  void createProduct() {
    webTestClient.post().uri("/v1/product")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(new CreateProductRequest("Iphone 1", "Best Phone Ever", 140.00)), CreateProductRequest.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Product.class)
        .consumeWith(productEntityExchangeResult -> {
          assertThat(productEntityExchangeResult.getResponseBody())
              .extracting("name", "description", "price")
              .containsExactly("Iphone 1", "Best Phone Ever", 140.00);
          assertThat(productEntityExchangeResult.getResponseBody()).isNotNull();
          assertThat(productEntityExchangeResult.getResponseBody().getId()).isNotNull();
        });
  }

  @Test
  void deleteById() {
    UUID uuid = UUID.randomUUID();
    Product product = new Product(uuid, "Tomato", "Ripe Tomatoes", 5.00);
    productRepository.save(product).block();

    webTestClient.delete().uri("/v1/product/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Void.class);
  }

  @Test
  void getProductById() {
    UUID uuid = UUID.randomUUID();
    Product product = new Product(uuid, "Tomato", "Ripe Tomatoes", 5.00);
    productRepository.save(product).block();

    webTestClient.get().uri("/v1/product/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class).consumeWith(productEntityExchangeResult -> {
      Product retrievedProduct = productEntityExchangeResult.getResponseBody();
      assertThat(retrievedProduct).isNotNull();
      assertThat(retrievedProduct.getId()).isEqualTo(uuid);
    });
  }

  @Test
  void getProductByIdNotFound() {
    UUID uuid = UUID.randomUUID();

    webTestClient.get().uri("/v1/product/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void shouldUpdateProduct() {
    UUID uuid = UUID.randomUUID();
    Product product = new Product(uuid, "Tomato", "Ripe Tomatoes", 5.00);
    productRepository.save(product).block();

    webTestClient.put().uri("/v1/product/" + uuid.toString())
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(new CreateProductRequest("Iphone 1", "Best Phone Ever", 140.00)), CreateProductRequest.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Product.class)
        .consumeWith(productEntityExchangeResult -> {
          assertThat(productEntityExchangeResult.getResponseBody())
              .extracting("name", "description", "price", "id")
              .containsExactly("Iphone 1", "Best Phone Ever", 140.00, uuid);
          assertThat(productEntityExchangeResult.getResponseBody()).isNotNull();
          assertThat(productEntityExchangeResult.getResponseBody().getId()).isNotNull();
        });
  }
}