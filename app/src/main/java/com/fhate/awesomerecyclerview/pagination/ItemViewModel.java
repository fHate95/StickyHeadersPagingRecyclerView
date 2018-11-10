package com.fhate.awesomerecyclerview.pagination;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import com.fhate.awesomerecyclerview.data.Item;
import com.fhate.awesomerecyclerview.util.DataBase;

public class ItemViewModel extends ViewModel {

    private Context context;
    private DataBase dataBase;

    MediatorLiveData<PagedList<Item>> uiList = new MediatorLiveData<PagedList<Item>>();
    LiveData<PagedList<Item>> pagedList;

    private ItemDataSourceFactory factory;

    public ItemViewModel(Context context, DataBase dataBase) {
        factory = new ItemDataSourceFactory(context, dataBase);
        this.context = context;
    }

    private PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .build();

    public void refresh() {
        if (pagedList != null) {
            factory.getDataSource().invalidate();
        } else {
            pagedList = new LivePagedListBuilder<>(factory, config).build();
            uiList.addSource(pagedList, hits -> {
                uiList.setValue(hits);
            });
        }
    }

    public MediatorLiveData<PagedList<Item>> getUiList() {
        return uiList;
    }

    public ItemDataSourceFactory getFactory() {
        return factory;
    }

    public PagedList.Config getConfig() {
        return config;
    }
}