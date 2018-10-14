package com.discoverorg.smartfridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.discoverorg.smartfridge.entity.AlertItem;
import com.discoverorg.smartfridge.entity.Item;
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
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(100.00);
    i.setUUID("b-1x");
    i.setName("Banana");

    /* check hash works */
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    assertEquals(1, smartFridge.getItems(i.getFillFactor()).length);

    i.setUUID("b-1xxxx");
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    assertEquals(2, smartFridge.getItems(i.getFillFactor()).length);
  }

  @Test
  public void itemRemoves() {
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(100.00);
    i.setUUID("b-1x");
    i.setName("Banana");
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    smartFridge.handleItemRemoved(i.getUUID());
    assertEquals(0, smartFridge.getItems(i.getFillFactor()).length);
  }

  @Test
  public void getItemsTypeBase() {
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(100.00);
    i.setUUID("b-1x");
    i.setName("Banana");

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    AlertItem b = new AlertItem();
    b.setFillFactor(i.getFillFactor());
    b.setType(i.getType());

    Object bFromList = smartFridge.getItems(i.getFillFactor())[0];
    assertEquals(b, bFromList);
  }

  @Test
  public void aveFillFactor() {
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(10.00);
    i.setUUID("b-1x");
    i.setName("Banana");

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    i.setUUID("c-1x");
    i.setFillFactor(30.00);

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    Double ave = 20.00;
    Double res = smartFridge.getFillFactor(1);
    assertEquals(ave, res);

    /* add another with different type.. ave shouldn't change */
    i.setUUID("d-1x");
    i.setType(2);
    i.setFillFactor(30.00);

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    assertEquals(ave, res);

    /**
    * add another of the same type, average should be ..
    * (10 + 30 + 1010545646878.17) / 3
    */
    i.setUUID("e-1x");
    i.setType(1);
    i.setFillFactor(1010545646878.17654564);

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    res = smartFridge.getFillFactor(1);
    assertNotEquals(ave, res);

    ave = (10 + 30 + i.getFillFactor()) / 3;

    /* Are the doubles really close */
    boolean nearly = Math.abs(res - ave) < .00001;
    assertTrue(nearly);
  }

  @Test
  public void ignoresTracking() {
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(10.00);
    i.setUUID("b-1x");
    i.setName("Item 1");

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    i.setUUID("c-1x");
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    i.setUUID("d-1x");
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    // Add a different type
    i.setUUID("e-1x");
    i.setType(2);
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    assertEquals(4, smartFridge.getItems(10.00).length);

    // forget item type 1
    smartFridge.forgetItem(1);
    assertEquals(1, smartFridge.getItems(10.00).length);
  }

  @Test
  public void testFillFactorAveWithZero() {
    Item i = new Item();
    i.setType(1);
    i.setFillFactor(60.00);
    i.setUUID("b-1x");
    i.setName("Item 1");

    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    i.setUUID("c-1x");
    i.setFillFactor(40.00);
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());

    Double ave = 50.00;
    assertEquals(ave, smartFridge.getFillFactor(1));

    // Add another with 0, average shouldnt' change
    i.setUUID("d-1x");
    i.setFillFactor(0.00);
    smartFridge.handleItemAdded(i.getType(), i.getUUID(), i.getName(), i.getFillFactor());
    assertEquals(ave, smartFridge.getFillFactor(1));
  }
}