package org.test.entities;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "test$Advertiser")
public class Advertiser {
  private Long id;
  private String name;
  private Status status;
  private List<Campaign> campaigns;

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

  public List<Campaign> getCampaigns() {
    return campaigns;
  }

  public void setCampaigns(List<Campaign> campaigns) {
    this.campaigns = campaigns;
  }
}
