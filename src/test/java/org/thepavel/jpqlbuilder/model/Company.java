package org.thepavel.jpqlbuilder.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity(name = "test_Company")
public class Company {
  @Id
  private Long id;
  private Status status;
  private String name;
  private List<Department> departments;
  private Map<Long, Employee> heads;

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

  public Map<Long, Employee> getHeads() {
    return heads;
  }

  public void setHeads(Map<Long, Employee> heads) {
    this.heads = heads;
  }
}
