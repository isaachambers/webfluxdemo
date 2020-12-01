package com.getir.web.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("v1/flux")
public class FluxController {

  @GetMapping(value = "/demo", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<Integer> getDemoValues() {
    return Flux.range(1, 10).log().delayElements(Duration.ofSeconds(1));
  }

  @GetMapping(value = "/normal")
  public Flux<Integer> getDemoNormalValues() {
    return Flux.just(1, 2, 3, 4);
  }

  @GetMapping(value = "/infinite", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<Long> getInfiniteLoop() {
    return Flux.interval(Duration.ofMillis(100)).log();
  }
}