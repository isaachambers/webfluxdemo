package com.getir.playground;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxCombineTest {
  @Test
  void combineWithMerge() {
    Flux<String> namesFlux = Flux.just("mark", "james");
    Flux<String> namesFlux2 = Flux.just("jimmy", "ben");

    Flux<String> mergedStringFlux = Flux.merge(namesFlux, namesFlux2);

    StepVerifier.create(mergedStringFlux.log())
        .expectSubscription()
        .expectNext("mark", "james", "jimmy", "ben")
        .verifyComplete();
  }

  @Test
  void combineWithMergeDelay() {
    Flux<String> namesFlux = Flux.just("mark", "james").delayElements(Duration.ofSeconds(1));
    Flux<String> namesFlux2 = Flux.just("jimmy", "ben").delayElements(Duration.ofSeconds(1));

    Flux<String> mergedStringFlux = Flux.merge(namesFlux, namesFlux2);

    StepVerifier.create(mergedStringFlux.log())
        .expectSubscription()
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  void combineWithConcat() {
    Flux<String> namesFlux = Flux.just("mark", "james");
    Flux<String> namesFlux2 = Flux.just("jimmy", "ben");

    Flux<String> mergedStringFlux = Flux.concat(namesFlux, namesFlux2);

    StepVerifier.create(mergedStringFlux.log())
        .expectSubscription()
        .expectNext("mark", "james", "jimmy", "ben")
        .verifyComplete();
  }

  @Test
  void combineWithConcatDelay() {
    Flux<String> namesFlux = Flux.just("mark", "james").delayElements(Duration.ofSeconds(1));
    Flux<String> namesFlux2 = Flux.just("jimmy", "ben").delayElements(Duration.ofSeconds(1));

    Flux<String> mergedStringFlux = Flux.concat(namesFlux, namesFlux2);

    StepVerifier.create(mergedStringFlux.log())
        .expectSubscription()
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  void combineWithZip() {
    Flux<String> namesFlux = Flux.just("A", "B");
    Flux<String> namesFlux2 = Flux.just("C", "D");

    Flux<String> mergedStringFlux = Flux.zip(namesFlux, namesFlux2, String::concat);

    StepVerifier.create(mergedStringFlux.log())
        .expectSubscription()
        .expectNext("AC", "BD")
        .verifyComplete();
  }
}
