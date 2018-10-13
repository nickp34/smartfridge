package com.discoverorg.smartfridge;

import com.discoverorg.smartfridge.service.SmartFridgeManagerImpl;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        SmartFridgeManagerImpl smartFridge = new SmartFridgeManagerImpl();
        long type = 1;
        String uuid = "dairy-1x";
        String name = "Milk";
        Double fill = 50.00;

        smartFridge.handleItemAdded(type, uuid, name, fill);
        // assertEquals(1, smartFridge.getItems(fill)[0]);
        Object[] x = smartFridge.getItems(fill);
        System.out.println(x);
    }
}
