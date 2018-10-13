
package com.discoverorg.smartfridge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.discoverorg.smartfridge.entity.BaseItem;
import com.discoverorg.smartfridge.entity.Item;

public class SmartFridgeManagerImpl implements SmartFridgeManager {

  HashMap<String, Item> items = new HashMap<>();

  public void handleItemRemoved(String itemUUID) {}

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
    List<BaseItem> itemList = new ArrayList<>();

    for (Item item : items.values()) {
      if (fillFactor >= item.getFillFactor() && item.getTracking()) {
        itemList.add(baseFromItem(item));
      }
    }

    return itemList.toArray(new BaseItem[itemList.size()]);
  }

  public Double getFillFactor(long itemType) {
    Double sum = 0.0;
    Integer count = 0;
    for (Item item : items.values()) {
      if (itemType == item.getType()) {
        sum += item.getFillFactor();
        count ++;
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

  private BaseItem baseFromItem(Item i) {
    BaseItem b = new BaseItem();
    b.setFillFactor(i.getFillFactor());
    b.setType(i.getType());
    return b;
  }
}