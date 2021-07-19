package io.github.uazw.todo.service;

import io.github.uazw.todo.domain.Task;
import io.github.uazw.todo.handler.dto.CreateTaskCommand;
import io.github.uazw.todo.handler.dto.UpdateTaskCommand;
import io.github.uazw.todo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoApplicationService {

  private TaskRepository repository;

  @Autowired
  public TodoApplicationService(TaskRepository repository) {
    this.repository = repository;
  }

  public Mono<Task> createTask(CreateTaskCommand command) {
    return repository.save(new Task(command.getName(), command.getDescription()));
  }

  public Flux<Task> findAll() {
    return repository.findAll();
  }

  public Mono<Task> updateTask(UpdateTaskCommand updateTaskCommand) {
    return repository.findById(updateTaskCommand.getTaskId())
        .doOnNext(task -> task.update(updateTaskCommand))
        .flatMap(task -> repository.save(task));
  }

  public Mono<Void> delete(long s) {
    return repository.deleteById(s);
  }
}
