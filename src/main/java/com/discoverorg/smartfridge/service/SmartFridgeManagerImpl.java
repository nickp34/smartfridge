
package com.discoverorg.smartfridge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.discoverorg.smartfridge.entity.DisplayItem;
import com.discoverorg.smartfridge.entity.Item;

public class SmartFridgeManagerImpl implements SmartFridgeManager {

  /**
   * Hashmap for storing items in the fridge
   */
  HashMap<String, Item> items = new HashMap<>();

  public void handleItemRemoved(String itemUUID) {
    items.remove(itemUUID);
  }

  /* Assuming we will just replace items based on their itemUUID */
  public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
    Item i = new Item();
    i.setType(itemType);
    i.setUUID(itemUUID);
    i.setName(name);
    i.setFillFactor(fillFactor);
    items.put(itemUUID, i);
  }

  /**
   * The comment above the interface for this method did not make sense to me, but does make sense
   * for the getFillFactor method.
      * "Unless all available containers are empty, this method should only consider the
      * non-empty containers when calculating the overall fillFactor for a given
      * item"
   */
  public Object[] getItems(Double fillFactor) {
    List<DisplayItem> itemList = new ArrayList<>();

    for (Item item : items.values()) {
      if (fillFactor >= item.getFillFactor() && item.getTracking()) {
        itemList.add(alertFromItem(item));
      }
    }

    return itemList.toArray(new DisplayItem[itemList.size()]);
  }

  /* I could filtered by getTracking but didn't only based on the comment above forgetItem */
  public Double getFillFactor(long itemType) {
    Double sum = 0.0;
    Integer count = 0;
    for (Item item : items.values()) {
      if (itemType == item.getType() && (item.getFillFactor() > 0.0)) {
        sum += item.getFillFactor();
        count++;
      }
    }
    return (count > 0) ? (sum / count) : 0.0;
  }

  public void forgetItem(long itemType) {
    for (Item item : items.values()) {
      if (itemType == item.getType()) {
        item.setTracking(false);
      }
    }
  }

  /**
   * Transforms an item into a DisplayItem sent back by getItems
   * @param i
   * @return DisplayItem
   */
  private DisplayItem alertFromItem(Item i) {
    DisplayItem b = new DisplayItem();
    b.setFillFactor(i.getFillFactor());
    b.setType(i.getType());
    return b;
  }
}