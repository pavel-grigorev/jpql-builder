package org.test.model;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "test_Company")
public class Company {
  private Long id;
  private Status status;
  private String name;
  private List<Department> departments;

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

  public List<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }
}
