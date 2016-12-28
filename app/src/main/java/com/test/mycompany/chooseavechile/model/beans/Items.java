package com.test.mycompany.chooseavechile.model.beans;

import java.util.ArrayList;
import java.util.List;

public class Items {
    private String mName;
    private boolean mOnline;

    public Items(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static List<Items> createItemsList(int numItems,String manufacturer, String name) {
        List<Items> items = new ArrayList<Items>();

        for (int i = 1; i <= numItems; i++) {
            items.add(new Items(++lastContactId +"Manufacturer " +manufacturer+ " " + name, i <= numItems / 2));
        }

        return items;
    }
}