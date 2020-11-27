package com.getir.controller;

import com.getir.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/mono")
public class MonoController {

  @GetMapping(value = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Person> getNames() {
    return Mono.just(new Person("Ben", 14));
  }
}