package com.getir.playground;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class VirtualTimeTest {

  @Test
  void testWithoutVirtualTime() {

    Flux<Integer> datIntegerFlux = Flux.range(1, 4).delayElements(Duration.ofSeconds(1)).take(3);

    StepVerifier.create(datIntegerFlux.log()).expectSubscription()
        .expectNext(1, 2, 3)
        .verifyComplete();

    //With this variation, Tests will run longer because of the the delayed emitting of elements.
  }


  @Test
  void testWithVirtualTime() {

    Flux<Integer> datIntegerFlux = Flux.range(1, 4).delayElements(Duration.ofSeconds(1)).take(3);

    StepVerifier.withVirtualTime(datIntegerFlux::log)
        .expectSubscription()
        .expectNext(1, 2, 3)
        .verifyComplete();

    //With this variation, Tests will run immediately without any wait
  }

}
