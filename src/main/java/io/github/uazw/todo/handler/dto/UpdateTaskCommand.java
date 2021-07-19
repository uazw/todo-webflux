package io.github.uazw.todo.handler.dto;

public class UpdateTaskCommand {

  private Long taskId;
  private String name;
  private String description;

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

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = Long.valueOf(taskId);
  }
}
