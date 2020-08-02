package org.test.entities;

import javax.persistence.Entity;

@Entity(name = "test$AdGroup")
public class AdGroup {
  private Long id;
  private String name;
  private Status status;
  private Campaign campaign;

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

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }
}
