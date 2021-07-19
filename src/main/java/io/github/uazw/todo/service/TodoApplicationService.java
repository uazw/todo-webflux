package io.github.uazw.todo.service;

import io.github.uazw.todo.domain.Task;
import io.github.uazw.todo.handler.CreateTaskCommand;
import io.github.uazw.todo.repo.InMenmoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TodoApplicationService {

  private InMenmoryRepository repository;

  @Autowired
  public TodoApplicationService(InMenmoryRepository repository) {
    this.repository = repository;
  }

  public Mono<Task> createTask(CreateTaskCommand command) {
    return repository.save(new Task(1, command.getName(), command.getDescription()));
  }
}
