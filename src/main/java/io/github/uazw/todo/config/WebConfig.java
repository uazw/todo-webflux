package io.github.uazw.todo.config;

import io.github.uazw.todo.exception.TodoValidationException;
import io.github.uazw.todo.handler.ErrorResponse;
import io.github.uazw.todo.handler.TodoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Configuration
public class WebConfig implements WebFluxConfigurer {

  private static <R extends ServerResponse> HandlerFunction<R> sup2Func(Supplier<Mono<R>> supplier) {
    return x -> supplier.get();
  }

  @Bean
  public RouterFunction<ServerResponse> todoRouter(TodoHandler handler) {
    return RouterFunctions.route()
        .path("/todo", builder -> builder
            .GET(sup2Func(handler::all)).POST(handler::create).PUT(handler::update))
        .filter(this::errorHandler)
        .build();
  }

  private Mono<ServerResponse> errorHandler(ServerRequest request, HandlerFunction<ServerResponse> next) {
    return next.handle(request).onErrorResume(TodoValidationException.class,
        (ex) -> ServerResponse.badRequest().bodyValue(new ErrorResponse(ex.getBindingResult())));
  }

}
