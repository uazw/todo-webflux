package io.github.uazw.todo.repo;

import io.github.uazw.todo.domain.Task;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMenmoryRepository {

  private Map<Long, Task> tasks;

  public InMenmoryRepository() {
    tasks = new HashMap<>(Map.of(
        1L, new Task(1, "wake me up", "chat with you"),
        2L, new Task(2, "wake me up", "chat with you"),
        3L, new Task(3, "wake me up", "chat with you")));
  }


  public Flux<Task> findAll() {
    return Flux.fromIterable(tasks.values());
  }


  public <S extends Task> Mono<S> save(S entity) {
    var taskId = entity.getTaskId();
    tasks.put(taskId, entity);
    return Mono.just(entity);
  }


  public <S extends Task> Flux<S> saveAll(Iterable<S> entities) {
    return null;
  }


  public <S extends Task> Flux<S> saveAll(Publisher<S> entityStream) {
    return null;
  }


  public Mono<Task> findById(Long s) {
    return Mono.justOrEmpty(tasks.get(s));
  }


  public Mono<Task> findById(Publisher<String> id) {
    return null;
  }


  public Mono<Boolean> existsById(String s) {
    return null;
  }


  public Mono<Boolean> existsById(Publisher<String> id) {
    return null;
  }

  public Flux<Task> findAllById(Iterable<String> strings) {
    return null;
  }


  public Flux<Task> findAllById(Publisher<String> idStream) {
    return null;
  }


  public Mono<Long> count() {
    return null;
  }


  public Mono<Void> deleteById(Long s) {
    tasks.remove(s);
    return Mono.empty();
  }


  public Mono<Void> deleteById(Publisher<String> id) {
    return null;
  }


  public Mono<Void> delete(Task entity) {
    return null;
  }


  public Mono<Void> deleteAllById(Iterable<? extends String> strings) {
    return null;
  }


  public Mono<Void> deleteAll(Iterable<? extends Task> entities) {
    return null;
  }


  public Mono<Void> deleteAll(Publisher<? extends Task> entityStream) {
    return null;
  }


  public Mono<Void> deleteAll() {
    return null;
  }
}
