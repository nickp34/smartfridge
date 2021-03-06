package com.discoverorg.smartfridge.entity;

import java.util.Objects;

/**
 * Just used for the array of objects returned by getItems
 */
public class DisplayItem {

  private long type;
  private Double fillFactor;

  public DisplayItem() {}

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

    DisplayItem DisplayItem = (DisplayItem) obj;
    return Objects.equals(type, DisplayItem.type) && Objects.equals(fillFactor, DisplayItem.fillFactor);
  }
}