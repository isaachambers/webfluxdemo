package com.getir.handler;

import com.getir.model.Person;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SamplePersonHandler {

  public Mono<ServerResponse> getPerson(ServerRequest serverRequest) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(new Person("Ben", 23)));
  }

  public Mono<ServerResponse> getPeople(ServerRequest serverRequest) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Flux.just(new Person("Mike", 12), new Person("Helen", 23)).log(), Person.class);
  }
}
