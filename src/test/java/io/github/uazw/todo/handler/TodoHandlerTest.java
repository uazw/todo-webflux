package io.github.uazw.todo.handler;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

class TodoHandlerTest {

  @Test
  void name() {
    var listMono = Flux.fromIterable(List.<String>of()).collectList().flatMap(list -> list.isEmpty() ? Mono.empty() : Mono.just(list)).switchIfEmpty(Mono.fromSupplier(() -> List.of("123")));

    System.out.println(listMono.block());
  }
}