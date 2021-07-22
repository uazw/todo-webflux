package io.github.uazw.todo.config;

import io.github.uazw.todo.exception.TodoValidationException;
import io.github.uazw.todo.handler.TodoHandler;
import io.github.uazw.todo.handler.dto.ErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.function.Supplier;

@Configuration
public class WebConfig implements WebFluxConfigurer {

  private static <R extends ServerResponse> HandlerFunction<R> sup2Func(Supplier<Mono<R>> supplier) {
    return x -> supplier.get();
  }

  @Bean
  public RouterFunction<ServerResponse> todoRouter(TodoHandler handler) {
    return RouterFunctions.route()
        .path("/todo", tasks -> tasks
            .GET(sup2Func(handler::all))
            .POST(handler::create)
            .nest(RequestPredicates.path("/{taskId}"), task ->
                task.PUT(handler::update).DELETE(handler::delete)))
        .filter(this::errorHandler)
        .build();
  }

  @NonNull
  private Mono<ServerResponse> errorHandler(ServerRequest request, HandlerFunction<ServerResponse> next) {
    return next.handle(request).onErrorResume(TodoValidationException.class,
        (ex) -> ServerResponse.badRequest().bodyValue(new ErrorResponse(ex.getBindingResult())));
  }

}
