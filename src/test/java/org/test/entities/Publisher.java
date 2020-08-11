package org.test.entities;

import javax.persistence.Entity;

@Entity(name = "test$Publisher")
public class Publisher {
  private Long id;
  private String name;
  private Status status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
