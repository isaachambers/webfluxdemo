package com.getir.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxErrorHandlingTest {

  @Test
  void testFluxError() {
    Flux<String> flux = Flux.just("a", "b", "c").concatWith(Flux.error(new RuntimeException("error")));

    StepVerifier.create(flux)
        .expectSubscription()
        .expectNext("a", "b", "c")
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  void testFluxOnErrorReturnResume() {
    Flux<String> flux = Flux.just("a", "b", "c")
        .concatWith(Flux.error(new RuntimeException("error")))
        .onErrorResume(throwable -> {
          System.out.println(throwable);
          return Flux.just("x", "y", "z");
        });

    StepVerifier.create(flux)
        .expectSubscription()
        .expectNext("a", "b", "c")
        .expectNext("x", "y", "z")
//        .expectError(RuntimeException.class)
        .verifyComplete();
  }

  @Test
  void testFluxOnErrorReturn() {
    Flux<String> flux = Flux.just("a", "b", "c").concatWith(Flux.error(new RuntimeException("error"))).onErrorReturn("d");

    StepVerifier.create(flux)
        .expectSubscription()
        .expectNext("a", "b", "c")
        .expectNext("d")
        .verifyComplete();
  }

  @Test
  void testFluxOnErrorWithRetry() {
    Flux<String> flux = Flux.just("a", "b", "c")
        .concatWith(Flux.error(new RuntimeException("error")))
        .concatWith(Flux.error(new RuntimeException("error")))
        .concatWith(Flux.error(new RuntimeException("error")))
        .concatWith(Flux.just("d"))
        .retry(3);

    StepVerifier.create(flux.log())
        .expectSubscription()
        .expectNext("a", "b", "c")
        .expectNext("a", "b", "c")
        .expectNext("a", "b", "c")
        .expectNext("a", "b", "c")
        .expectError(RuntimeException.class)
        .verify();
  }
}
