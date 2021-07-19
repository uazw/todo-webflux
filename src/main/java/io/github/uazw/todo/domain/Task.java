package io.github.uazw.todo.domain;

import io.github.uazw.todo.handler.dto.UpdateTaskCommand;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

  private long taskId;
  private String name;
  private String description;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;

  public Task() {
  }

  public Task(long taskId, String name, String description) {
    this.taskId = taskId;
    this.name = name;
    this.description = description;
    var now = LocalDateTime.now();
    this.createAt = now;
    this.updateAt = now;
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

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return taskId == task.taskId && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(createAt, task.createAt) && Objects.equals(updateAt, task.updateAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, name, description, createAt, updateAt);
  }

  public void update(UpdateTaskCommand updateTaskCommand) {
    name = updateTaskCommand.getName();
    description = updateTaskCommand.getDescription();
  }
}
