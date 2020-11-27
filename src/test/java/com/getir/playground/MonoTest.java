package com.getir.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

  @Test
  void testMono() {
    Mono<String> monoVal = Mono.just("message1").log();

    StepVerifier.create(monoVal).expectNext("message1").verifyComplete();
  }

  @Test
  void testMonoWithError() {
    Mono<String> monoVal = Mono.error(new RuntimeException("error"));

    StepVerifier.create(monoVal).expectError().verify();
  }
}
