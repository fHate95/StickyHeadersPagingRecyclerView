package com.fhate.awesomerecyclerview.util;

import android.content.Context;

import com.fhate.awesomerecyclerview.R;
import com.fhate.awesomerecyclerview.data.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataBase {

    private Context context;

    private ArrayList<Item> items;
    private String[] data;

    public DataBase(Context context) {
        this.context = context;
        items = new ArrayList<>();
        data = context.getString(R.string.words_data).split(" ");
    }

    public void init() {
        for (int i = 0; i < data.length; i++) {
            items.add(new Item(data[i], 100, i + 1));
        }
        if (!items.isEmpty()) {
            Collections.sort(items, (object1, object2) -> object1.getName().compareTo(object2.getName()));
        }
    }

    public int getDataSize() {
        return items.size();
    }

    public ArrayList<Item> get(int start, int offset) {
        ArrayList<Item> data = new ArrayList<>();
        if ((start + offset) >= getDataSize()) {
            return data;
        } else {
            for (int i = start; i < offset + start; i++) {
                data.add(items.get(i));
            }
            return data;
        }
    }

    public ArrayList<Item> getAll() {
        return items;
    }
}
