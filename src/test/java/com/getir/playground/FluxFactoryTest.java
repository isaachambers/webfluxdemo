package com.getir.playground;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxFactoryTest {

  @Test
  void fluxFromIterable() {
    List<String> names = Arrays.asList("mark", "james");

    Flux<String> namesFlux = Flux.fromIterable(names);

    StepVerifier.create(namesFlux).expectNextCount(2).verifyComplete();
  }

  @Test
  void fluxFromArray() {
    String[] names = {"mark", "james"};

    Flux<String> namesFlux = Flux.fromArray(names);

    StepVerifier.create(namesFlux).expectNextCount(2).verifyComplete();
  }

  @Test
  void fluxFromStream() {
    List<String> names = Arrays.asList("mark", "james");

    Flux<String> namesFlux = Flux.fromStream(names.stream());

    StepVerifier.create(namesFlux).expectNextCount(2).verifyComplete();
  }


  @Test
  void fluxFromRange() {

    Flux<Integer> namesFlux = Flux.range(1, 6).log();

    StepVerifier.create(namesFlux).expectNext(1, 2, 3, 4, 5, 6).verifyComplete();
  }

  @Test
  void fluxFilterTest() {
    List<String> names = Arrays.asList("mark", "james");

    Flux<String> namesFlux = Flux.fromStream(names.stream())
        .filter(s -> s.contains("k"));
    StepVerifier.create(namesFlux).expectNextCount(1).verifyComplete();
  }
}
