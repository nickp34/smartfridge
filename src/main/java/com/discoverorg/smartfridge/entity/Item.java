package com.discoverorg.smartfridge.entity;

import java.util.Objects;

public class Item extends AlertItem {

  private String UUID;
  private String name;
  private boolean isTracking = true;

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
      && Objects.equals(isTracking, item.isTracking);
  }
}