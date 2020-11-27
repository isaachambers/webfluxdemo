package com.getir.playground;

import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoFactoryTest {

  @Test
  void usingEmptyOrJust() {
    Mono<String> stringMono = Mono.justOrEmpty(null);

    StepVerifier.create(stringMono.log()).verifyComplete();
  }

  @Test
  void usingSupplier() {

    Supplier<String> supplier = () -> "name";
    Mono<String> stringMono = Mono.fromSupplier(supplier);

    StepVerifier.create(stringMono.log()).expectNext("name").verifyComplete();
  }
}
