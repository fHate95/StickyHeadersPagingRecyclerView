package com.fhate.awesomerecyclerview.ui;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.fhate.awesomerecyclerview.R;
import com.fhate.awesomerecyclerview.adapter.PagingAdapter;
import com.fhate.awesomerecyclerview.data.Item;
import com.fhate.awesomerecyclerview.decoration.HeaderItemDecoration;
import com.fhate.awesomerecyclerview.decoration.RecyclerSectionItemDecoration;
import com.fhate.awesomerecyclerview.pagination.ItemViewModel;
import com.fhate.awesomerecyclerview.util.DataBase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private PagingAdapter pagingAdapter;
    private ItemViewModel viewModel;

    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        dataBase = new DataBase(this);
        dataBase.init();

        setRecyclerView();

        /* View click listeners */
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
        });
    }

    private void setRecyclerView() {
        pagingAdapter = new PagingAdapter(new DiffUtil.ItemCallback<Item>() {
            @Override
            public boolean areItemsTheSame(@NonNull Item item, @NonNull Item t1) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Item item, @NonNull Item t1) {
                return false;
            }
        }, this, new PagingAdapter.AdapterClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });

        viewModel = new ItemViewModel(MainActivity.this, dataBase);

        viewModel.getUiList().observe(this, hits -> {
            pagingAdapter.submitList(hits);
            swipeRefreshLayout.setRefreshing(false);
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(pagingAdapter);

        RecyclerSectionItemDecoration sectionItemDecoration =
                new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                        true,
                        (RecyclerSectionItemDecoration.SectionCallback) pagingAdapter);
        recyclerView.addItemDecoration(sectionItemDecoration);

        //recyclerView.addItemDecoration(new HeaderItemDecoration(recyclerView, (HeaderItemDecoration.StickyHeaderInterface) pagingAdapter));

        viewModel.refresh();
    }

//    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<Item> people) {
//        return new RecyclerSectionItemDecoration.SectionCallback() {
//            @Override
//            public boolean isSection(int position) {
//                return position == 0
//                        || people.get(position)
//                        .getName()
//                        .charAt(0) != people.get(position - 1)
//                        .getName()
//                        .charAt(0);
//            }
//
//            @Override
//            public CharSequence getSectionHeader(int position) {
//                return people.get(position)
//                        .getName()
//                        .subSequence(0,
//                                1);
//            }
//        };
//    }
}