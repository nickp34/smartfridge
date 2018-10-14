package com.discoverorg.smartfridge.entity;

import java.util.Objects;

public class AlertItem {

  private long type;
  private Double fillFactor;

  public AlertItem() {}

  public void setType(long type) {
    this.type = type;
  }

  public long getType() {
    return this.type;
  }

  public void setFillFactor(Double fillFactor) {
    this.fillFactor = fillFactor;
  }

  public Double getFillFactor() {
    return this.fillFactor;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    AlertItem AlertItem = (AlertItem) obj;
    return Objects.equals(type, AlertItem.type) && Objects.equals(fillFactor, AlertItem.fillFactor);
  }
}