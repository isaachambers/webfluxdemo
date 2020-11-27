package com.getir.playground;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdAndHotPublisherTest {

  @Test
  void coldPublisherTest() throws InterruptedException {
    //Cold Publisher emits elements from the start for each subscriber added on the flux

    Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1)).log();

    stringFlux.subscribe(s -> {
      System.out.println("Subscriber 1 " + s);
    });

    Thread.sleep(4000);
    stringFlux.subscribe(s -> {
      System.out.println("Subscriber 2 " + s);
    });

    Thread.sleep(4000);
  }

  @Test
  void hotPublisherTest() throws InterruptedException {
    //Hot Publisher does not emit elements from the start for each subscriber added on the flux

    Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1)).log();

    ConnectableFlux<String> stringConnectableFlux = stringFlux.publish();
    stringConnectableFlux.connect();// makes it behave as a hot publisher

    stringConnectableFlux.subscribe(s -> {
      System.out.println("Subscriber 1 " + s);
    });

    Thread.sleep(4000);

    stringConnectableFlux.subscribe(s -> {
      System.out.println("Subscriber 2 " + s);
    });

    Thread.sleep(4000);
  }
}
