package com.getir.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxBackPressureTest {

  @Test
  void backPressureTest() {
    Flux<Integer> finiteFlux = Flux.range(1, 10);

    StepVerifier.create(finiteFlux)
        .expectSubscription()
        .thenRequest(1)
        .expectNext(1)
        .thenRequest(2)
        .expectNext(2)
        .expectNext(3)
        .thenCancel()
        .verify();
  }

  @Test
  void backPressureRequest() {
    Flux<Integer> finiteFlux = Flux.range(1, 10);

    finiteFlux.subscribe(integer -> System.out.println(integer), throwable -> {
      System.err.println(throwable.getMessage());
    }, () -> {
      System.out.println("Done");
    }, subscription -> {
      subscription.request(2);
    });
  }

  @Test
  void backPressureRequestCancel() {
    Flux<Integer> finiteFlux = Flux.range(1, 10);

    finiteFlux.subscribe(integer -> System.out.println(integer), throwable -> {
      System.err.println(throwable.getMessage());
    }, () -> {
      System.out.println("Done");
    }, subscription -> {
      subscription.cancel();
    });
  }

  @Test
  void backPressureCustomized() {
    Flux<Integer> finiteFlux = Flux.range(1, 10).log();

    finiteFlux.subscribe(new BaseSubscriber<>() {
      @Override
      protected void hookOnNext(Integer value) {
        request(1);
        System.out.println("Value is " + value);
        if (value == 4) {
          cancel();
        }
      }
    });
  }
}
