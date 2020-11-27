package com.getir.controller;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@WebFluxTest
class FluxControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  void shouldRetrieveCorrectAmountOfValues() {
    Flux<Integer> integer = webTestClient.get().uri("/v1/flux/demo")
        .accept(MediaType.APPLICATION_NDJSON)
        .exchange()
        .expectStatus()
        .isOk()
        .returnResult(Integer.class)
        .getResponseBody();

    StepVerifier.withVirtualTime(() -> integer)
        .expectSubscription()
        .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .verifyComplete();
  }

  @Test
  void getDemoNormalValues() {

    webTestClient.get().uri("/v1/flux/normal")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Integer.class)
        .hasSize(4);
  }

  @Test
  void getDemoNormalValuesMethodTwo() {
    List<Integer> expectedIntegers = Arrays.asList(1, 2, 3, 4);

    EntityExchangeResult<List<Integer>> response = webTestClient.get().uri("/v1/flux/normal")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Integer.class)
        .returnResult();

    assertEquals(expectedIntegers, response.getResponseBody());
  }

  @Test
  void getDemoNormalValuesMethodAlternative() {
    List<Integer> expectedIntegers = Arrays.asList(1, 2, 3, 4);

    webTestClient.get().uri("/v1/flux/normal")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Integer.class)
        .consumeWith(listEntityExchangeResult -> {
          assertEquals(expectedIntegers, listEntityExchangeResult.getResponseBody());
        });
  }

  @Test
  void shouldRetrieveCorrectAmountOfValuesFromInfinite() {
    Flux<Long> integer = webTestClient.get().uri("/v1/flux/infinite")
        .accept(MediaType.APPLICATION_NDJSON)
        .exchange()
        .expectStatus()
        .isOk()
        .returnResult(Long.class)
        .getResponseBody();

    StepVerifier.create(integer)
        .expectSubscription()
        .expectNext(0L, 1L, 2L, 3L)
        .thenCancel()
        .verify();
  }
}