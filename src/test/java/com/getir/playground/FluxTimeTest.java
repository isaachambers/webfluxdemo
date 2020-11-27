package com.getir.playground;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTimeTest {


  @Test
  public void testInfiniteSequence() throws InterruptedException {
    Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(100))
        .log();

    infiniteFlux.subscribe(System.out::println);

    Thread.sleep(3000);
  }

  @Test
  public void testFiniteSequence() throws InterruptedException {
    Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(100))
        .take(3)
        .log();

    StepVerifier.create(finiteFlux)
        .expectSubscription()
        .expectNext(0L, 1L, 2L).verifyComplete();
  }

}