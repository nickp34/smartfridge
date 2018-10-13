package com.discoverorg.smartfridge.entity;

import java.util.Objects;

public class BaseItem {

  private long type;
  private Double fillFactor;

  public BaseItem() {
  }

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

    BaseItem baseItem = (BaseItem) obj;
    return Objects.equals(type, baseItem.type) && Objects.equals(fillFactor, baseItem.fillFactor);
  }
}