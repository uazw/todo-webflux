package io.github.uazw.todo.handler;

import io.github.uazw.todo.domain.Task;
import io.github.uazw.todo.exception.TodoValidationException;
import io.github.uazw.todo.repo.InMenmoryRepository;
import io.github.uazw.todo.service.TodoApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class TodoHandler {

  private final InMenmoryRepository taskMongoRepository;
  private final Validator validator;
  private final TodoApplicationService todoApplicationService;

  @Autowired
  public TodoHandler(InMenmoryRepository taskMongoRepository, Validator validator, TodoApplicationService todoApplicationService) {
    this.taskMongoRepository = taskMongoRepository;
    this.validator = validator;
    this.todoApplicationService = todoApplicationService;
  }

  private static Mono<? extends ServerResponse> okWithBody(Task task) {
    return ok().bodyValue(task);
  }

  public Mono<ServerResponse> all() {
    return taskMongoRepository.findAll()
        .collectList().flatMap(tasks -> ok().bodyValue(tasks));
  }

  public @NonNull
  Mono<ServerResponse> create(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateTaskCommand.class)
        .doOnNext(this::validate)
        .flatMap(todoApplicationService::createTask)
        .flatMap(TodoHandler::okWithBody);
  }

  public Mono<ServerResponse> update(ServerRequest serverRequest) {
    return null;
  }

  private <T> void validate(T command) {
    Errors errors = new BeanPropertyBindingResult(command, "task");
    validator.validate(command, errors);
    if (errors.hasErrors()) {
      throw new TodoValidationException(errors);
    }
  }
}
