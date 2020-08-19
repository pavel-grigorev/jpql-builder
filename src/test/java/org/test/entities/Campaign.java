package org.test.entities;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "test$Campaign")
public class Campaign {
  private Long id;
  private String name;
  private Status status;
  private Advertiser advertiser;
  private List<AdGroup> adGroups;
  private Boolean active;

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

  public Advertiser getAdvertiser() {
    return advertiser;
  }

  public void setAdvertiser(Advertiser advertiser) {
    this.advertiser = advertiser;
  }

  public List<AdGroup> getAdGroups() {
    return adGroups;
  }

  public void setAdGroups(List<AdGroup> adGroups) {
    this.adGroups = adGroups;
  }

  public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
