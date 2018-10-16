package com.discoverorg.smartfridge.entity;

import java.util.Objects;

public class Item {

  private String UUID;
  private String name;
  private boolean isTracking = true;
  private long type;
  private Double fillFactor;

  private static Double MIN_FILL = 0.0;
  private static Double MAX_FILL = 100.0;

  public Item() {}

  public void setUUID(String UUID) {
    this.UUID = UUID;
  }

  public String getUUID() {
    return this.UUID;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setTracking(boolean isTracking) {
    this.isTracking = isTracking;
  }

  public boolean getTracking() {
    return this.isTracking;
  }

  public void setType(long type) {
    this.type = type;
  }

  public long getType() {
    return this.type;
  }

  public void setFillFactor(Double fillFactor) {
    if (fillFactor > MAX_FILL) {
      this.fillFactor = MAX_FILL;
    } else if (fillFactor < MIN_FILL) {
      this.fillFactor = MIN_FILL;
    } else {
      this.fillFactor = fillFactor;
    }
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

    Item item = (Item) obj;
    return Objects.equals(UUID, item.UUID)
      && Objects.equals(name, item.name)
      && Objects.equals(isTracking, item.isTracking)
      && Objects.equals(type, item.type)
      && Objects.equals(fillFactor, item.fillFactor);
  }
}