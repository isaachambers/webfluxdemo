package com.getir.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


import com.getir.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
class MonoControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  void shouldGetPerson() {
    Person expectedPerson = new Person("Ben", 14);

    webTestClient.get().uri("/v1/mono/default")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Person.class)
        .consumeWith(response -> {
          assertThat(response.getResponseBody()).extracting("name", "age")
              .containsExactly(expectedPerson.getName(), expectedPerson.getAge());
        });
  }
}