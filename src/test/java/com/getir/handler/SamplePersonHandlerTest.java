package com.getir.handler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.getir.IntegrationTest;
import com.getir.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SamplePersonHandlerTest extends IntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  void getPerson() {
    webTestClient.get().uri("/v1/functional/person")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Person.class)
        .consumeWith(personFluxExchangeResult -> {
          assertThat(personFluxExchangeResult.getResponseBody()).extracting("name", "age").containsExactly("Ben", 23);
        });
  }

  @Test
  void getPeople() {
    Flux<Person> personFlux = webTestClient.get().uri("/v1/functional/people")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .returnResult(Person.class)
        .getResponseBody();

    StepVerifier.create(personFlux)
        .expectSubscription()
        .expectNextCount(2)
        .verifyComplete();
  }
}