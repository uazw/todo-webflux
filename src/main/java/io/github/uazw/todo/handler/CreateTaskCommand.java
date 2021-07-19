package io.github.uazw.todo.handler;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class CreateTaskCommand {

  @Pattern(regexp = "^[a-zA-Z0-9]{1,50}$")
  private String name;
  @Length(max = 500)
  private String description;

  public CreateTaskCommand() {
  }

  public CreateTaskCommand(String name, String description) {
    this.name = name;
    this.description = description;
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
}
