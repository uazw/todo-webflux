package io.github.uazw.todo.domain;

import io.github.uazw.todo.handler.dto.UpdateTaskCommand;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

  @Id
  private long taskId;
  private String name;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Task() {
  }

  public Task(String name, String description) {
    this.name = name;
    this.description = description;
    var now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  public long getTaskId() {
    return taskId;
  }

  public void setTaskId(long taskId) {
    this.taskId = taskId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return taskId == task.taskId && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(createdAt, task.createdAt) && Objects.equals(updatedAt, task.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, name, description, createdAt, updatedAt);
  }

  public void update(UpdateTaskCommand updateTaskCommand) {
    name = updateTaskCommand.getName();
    description = updateTaskCommand.getDescription();
  }
}
