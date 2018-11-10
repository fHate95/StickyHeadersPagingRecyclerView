package com.fhate.awesomerecyclerview.data;

public class Item {

    public static final int TYPE_ITEM = 1;
    public static final int TYPE_SECTION = 2;

    private String name;
    private long price = 0L;
    private int count = 0;
    private int viewType = TYPE_ITEM;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, long price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public int getViewType() {
        return viewType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
