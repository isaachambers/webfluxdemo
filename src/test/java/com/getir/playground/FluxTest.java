package com.getir.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

  @Test
  public void fluxElementsWithoutError() {
    Flux<String> messages = Flux.just("message1", "message2", "message3");

    StepVerifier.create(messages)
        .expectNext("message1")
        .expectNext("message2")
        .expectNext("message3")
        .expectComplete().verify();
  }

  @Test
  public void fluxElementsInlineWithoutError() {
    Flux<String> messages = Flux.just("message1", "message2", "message3");

    StepVerifier.create(messages)
        .expectNext("message1", "message2", "message3")
        .expectComplete().verify();
  }

  @Test
  public void fluxElementsWithError() {
    Flux<String> messages = Flux.just("message1", "message2", "message3").concatWith(Flux.error(new RuntimeException("Exception")));

    StepVerifier.create(messages)
        .expectNext("message1")
        .expectNext("message2")
        .expectNext("message3")
        .expectError(RuntimeException.class).verify();
  }

  @Test
  public void fluxElementsWithErrorMessage() {
    Flux<String> messages = Flux.just("message1", "message2", "message3").concatWith(Flux.error(new RuntimeException("Exception")));

    StepVerifier.create(messages)
        .expectNext("message1")
        .expectNext("message2")
        .expectNext("message3")
        .expectErrorMessage("Exception").verify();
  }

  @Test
  public void fluxElementsCountWithErrorMessage() {
    Flux<String> messages = Flux.just("message1", "message2", "message3").concatWith(Flux.error(new RuntimeException("Exception")));

    StepVerifier.create(messages)
        .expectNextCount(3)
        .expectErrorMessage("Exception").verify();
  }
}
