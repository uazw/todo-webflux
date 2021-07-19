package io.github.uazw.todo.handler;

import io.github.uazw.todo.exception.TodoValidationException;
import io.github.uazw.todo.handler.dto.CreateTaskCommand;
import io.github.uazw.todo.handler.dto.TaskResponse;
import io.github.uazw.todo.handler.dto.UpdateTaskCommand;
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

  public static final String TASK_ID_VARIABLE = "taskId";
  private final Validator validator;
  private final TodoApplicationService todoApplicationService;

  @Autowired
  public TodoHandler(Validator validator, TodoApplicationService todoApplicationService) {
    this.validator = validator;
    this.todoApplicationService = todoApplicationService;
  }

  private static <T> Mono<ServerResponse> okWithBody(T t) {
    return ok().bodyValue(t);
  }

  public Mono<ServerResponse> all() {
    return todoApplicationService.findAll()
        .map(TaskResponse::new)
        .collectList().flatMap(tasks -> ok().bodyValue(tasks));
  }

  public @NonNull
  Mono<ServerResponse> create(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateTaskCommand.class)
        .doOnNext(this::validate)
        .flatMap(todoApplicationService::createTask)
        .map(TaskResponse::new)
        .flatMap(TodoHandler::okWithBody);
  }

  public Mono<ServerResponse> update(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UpdateTaskCommand.class)
        .doOnNext(command -> command.setTaskId(serverRequest.pathVariable(TASK_ID_VARIABLE)))
        .doOnNext(this::validate)
        .flatMap(todoApplicationService::updateTask)
        .map(TaskResponse::new)
        .flatMap(TodoHandler::okWithBody)
        .switchIfEmpty(Mono.defer(() -> ServerResponse.notFound().build()));
  }

  private <T> void validate(T command) {
    Errors errors = new BeanPropertyBindingResult(command, "task");
    validator.validate(command, errors);
    if (errors.hasErrors()) {
      throw new TodoValidationException(errors);
    }
  }

  public Mono<ServerResponse> delete(ServerRequest serverRequest) {
    return Mono.just(serverRequest.pathVariable(TASK_ID_VARIABLE))
        .map(Long::valueOf)
        .doOnNext(todoApplicationService::delete)
        .flatMap(x -> Mono.defer(() -> ServerResponse.noContent().build()));
  }
}
