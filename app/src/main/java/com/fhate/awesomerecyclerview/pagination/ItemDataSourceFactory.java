package com.fhate.awesomerecyclerview.pagination;

import android.arch.paging.DataSource;
import android.content.Context;

import com.fhate.awesomerecyclerview.data.Item;
import com.fhate.awesomerecyclerview.util.DataBase;

public class ItemDataSourceFactory extends DataSource.Factory<Integer, Item> {
    ItemDataSource dataSource;

    private Context context;
    private DataBase dataBase;
    private String searchQuery;

    public ItemDataSourceFactory(Context context, DataBase dataBase) {
        this.context = context;
        this.dataBase = dataBase;
    }

    @Override
    public DataSource<Integer, Item> create() {
        dataSource = new ItemDataSource(context, dataBase);
        return dataSource;
    }

    public ItemDataSource getDataSource() {
        return dataSource;
    }
}
