package com.getir.router;

import com.getir.handler.ProductHandler;
import com.getir.handler.SamplePersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class AppRouter {

  @Bean
  public RouterFunction<ServerResponse> route(SamplePersonHandler personHandler, ProductHandler productHandler) {
    return RouterFunctions
        .route(RequestPredicates.GET("/v1/functional/person").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), personHandler::getPerson)
        .andRoute(RequestPredicates.GET("/v1/functional/people").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), personHandler::getPeople)
        .andRoute(RequestPredicates.GET("v1/functional/product").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), request -> productHandler.getAllProducts());
  }
}