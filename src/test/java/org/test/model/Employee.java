package org.test.model;

import javax.persistence.Entity;

@Entity(name = "test_Employee")
public class Employee {
  private Long id;
  private Status status;
  private String name;
  private Department department;
  private Boolean headOfDepartment;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Boolean isHeadOfDepartment() {
    return headOfDepartment;
  }

  public void setHeadOfDepartment(Boolean headOfDepartment) {
    this.headOfDepartment = headOfDepartment;
  }
}