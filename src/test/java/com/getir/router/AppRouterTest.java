package com.getir.router;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;


import com.getir.IntegrationTest;
import com.getir.document.Product;
import com.getir.repository.ProductRepository;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class AppRouterTest extends IntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  ProductRepository productRepository;

  @Test
  void getAllProducts() {
    productRepository
        .saveAll(Arrays.asList(new Product(UUID.randomUUID(), "Salt", "Normal Salt", 12.45),
            new Product(UUID.randomUUID(), "Tomato", "Ripe Tomatoes", 5.00),
            new Product(UUID.randomUUID(), "Coffee", "Coffee Beans", 45.3),
            new Product(UUID.randomUUID(), "Ketchup", "Hot and Sweet", 25.00)))
        .blockLast();


    webTestClient.get().uri("/v1/functional/product")
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

}