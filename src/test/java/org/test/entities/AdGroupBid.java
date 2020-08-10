package org.test.entities;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "test$AdGroupBid")
public class AdGroupBid {
  private Long id;
  private AdGroup adGroup;
  private BigDecimal value;
  private Boolean active;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AdGroup getAdGroup() {
    return adGroup;
  }

  public void setAdGroup(AdGroup adGroup) {
    this.adGroup = adGroup;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
