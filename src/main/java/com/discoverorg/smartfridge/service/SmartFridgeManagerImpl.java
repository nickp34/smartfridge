
package com.discoverorg.smartfridge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.discoverorg.smartfridge.entity.AlertItem;
import com.discoverorg.smartfridge.entity.Item;

public class SmartFridgeManagerImpl implements SmartFridgeManager {

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

  public Object[] getItems(Double fillFactor) {
    List<AlertItem> itemList = new ArrayList<>();

    for (Item item : items.values()) {
      if (fillFactor >= item.getFillFactor() && item.getTracking()) {
        itemList.add(alertFromItem(item));
      }
    }

    return itemList.toArray(new AlertItem[itemList.size()]);
  }

  public Double getFillFactor(long itemType) {
    Double sum = 0.0;
    Integer count = 0;
    for (Item item : items.values()) {
      if (itemType == item.getType() && (item.getFillFactor() > 0.0)) {
        sum += item.getFillFactor();
        count++;
      }
    }
    return sum  / count;
  }

  public void forgetItem(long itemType) {
    for (Item item : items.values()) {
      if (itemType == item.getType()) {
        item.setTracking(false);
      }
    }
  }

  private AlertItem alertFromItem(Item i) {
    AlertItem b = new AlertItem();
    b.setFillFactor(i.getFillFactor());
    b.setType(i.getType());
    return b;
  }
}