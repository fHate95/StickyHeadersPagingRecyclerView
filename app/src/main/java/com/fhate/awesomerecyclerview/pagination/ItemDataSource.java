package com.fhate.awesomerecyclerview.pagination;

import android.arch.paging.PositionalDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fhate.awesomerecyclerview.data.Item;
import com.fhate.awesomerecyclerview.util.DataBase;

import java.util.ArrayList;

public class ItemDataSource extends PositionalDataSource<Item> {

    private final String TAG = "ItemDataSource";

    private DataBase dataBase;
    private Context context;

    public ItemDataSource(Context context, DataBase dataBase) {
        this.dataBase = dataBase;
        this.context = context;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Item> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);

        ArrayList<Item> items = dataBase.get(0, 10);
//        items.get(0).setViewType(Item.TYPE_SECTION);
        callback.onResult(items, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Item> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);

        ArrayList<Item> items = dataBase.get(params.startPosition, params.startPosition + params.loadSize);
        callback.onResult(items);
    }
}
