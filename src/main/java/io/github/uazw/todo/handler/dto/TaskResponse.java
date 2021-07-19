package io.github.uazw.todo.handler.dto;

import io.github.uazw.todo.domain.Task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class TaskResponse {

  private final long taskId;
  private final String name;
  private final String description;
  private final String createAt;
  private final String updateAt;

  public TaskResponse(Task task) {
    this(task.getTaskId(), task.getName(), task.getDescription(), getOf(task.getCreatedAt()), getOf(task.getUpdatedAt()));
  }

  public TaskResponse(long taskId, String name, String description, String createAt, String updateAt) {
    this.taskId = taskId;
    this.name = name;
    this.description = description;
    this.createAt = createAt;
    this.updateAt = updateAt;
  }

  private static String getOf(LocalDateTime localDateTime) {
    return ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(ISO_OFFSET_DATE_TIME);
  }

  public long getTaskId() {
    return taskId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getCreateAt() {
    return createAt;
  }

  public String getUpdateAt() {
    return updateAt;
  }
}
