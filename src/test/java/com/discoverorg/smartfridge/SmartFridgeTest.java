package com.discoverorg.smartfridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.discoverorg.smartfridge.entity.BaseItem;
import com.discoverorg.smartfridge.service.SmartFridgeManagerImpl;

import org.junit.Before;
import org.junit.Test;

public class SmartFridgeTest {

  private SmartFridgeManagerImpl smartFridge;

  @Before
  public void initialize() {
     smartFridge = new SmartFridgeManagerImpl();
  }

  @Test
  public void itemAdds() {
    long type = 1;
    String uuid = "b-1x";
    String name = "Chiquita Banana";
    Double fill = 100.00;
    smartFridge.handleItemAdded(type, uuid, name, fill);
    smartFridge.handleItemAdded(type, uuid, name, fill);
    // assertEquals(fill, smartFridge.getFillFactor(type));
    assertEquals(1, smartFridge.getItems(fill).length);

    uuid = "b-1xx";
    smartFridge.handleItemAdded(type, uuid, name, fill);
    assertEquals(2, smartFridge.getItems(fill).length);
  }

  @Test
  public void getItemsTypeBase() {
    long type = 1;
    String uuid = "dairy-1x";
    String name = "Milk";
    Double fill = 50.00;

    smartFridge.handleItemAdded(type, uuid, name, fill);

    BaseItem b = new BaseItem();
    b.setFillFactor(fill);
    b.setType(type);

    Object bFromList = smartFridge.getItems(fill)[0];
    assertEquals(b, bFromList);
  }

  @Test
  public void aveFillFactor() {
    long type = 1;
    String uuid = "b-1x";
    String name = "Item 1";
    Double fill = 10.00;

    smartFridge.handleItemAdded(type, uuid, name, fill);

    uuid = "c-1x";
    type = 1;
    fill = 30.00;

    smartFridge.handleItemAdded(type, uuid, name, fill);

    Double ave = 20.00;
    Double res = smartFridge.getFillFactor(1);
    assertEquals(ave, res);

    /* add another with different type.. ave shouldn't change */
    uuid = "d-1x";
    type = 2;
    fill = 30.00;

    smartFridge.handleItemAdded(type, uuid, name, fill);
    assertEquals(ave, res);

    /**
    * add another of the same type, average should be ..
    * (10 + 30 + 1010545646878.17) / 3
    */
    uuid = "e-1x";
    type = 1;
    fill = 1010545646878.17654564;

    smartFridge.handleItemAdded(type, uuid, name, fill);
    res = smartFridge.getFillFactor(1);
    assertNotEquals(ave, res);

    ave = (10 + 30 + fill) / 3;

    /* Are the doubles really close */
    boolean close = Math.abs(res - ave) < .00001;
    assertTrue(close);
  }

  @Test
  public void ignoresTracking() {
    long type = 1;
    String uuid = "b-1x";
    String name = "Item 1";
    Double fill = 10.00;

    smartFridge.handleItemAdded(type, uuid, name, fill);

    uuid = "c-1x";
    smartFridge.handleItemAdded(type, uuid, name, fill);

    uuid = "d-1x";
    smartFridge.handleItemAdded(type, uuid, name, fill);

    // Add a different type

    uuid = "e-1x";
    type = 2;
    smartFridge.handleItemAdded(type, uuid, name, fill);

    assertEquals(4, smartFridge.getItems(10.00).length);

    // forget item type 1
    smartFridge.forgetItem(1);
    // assertEquals(1, smartFridge.getItems(10.00).length);
  }
}