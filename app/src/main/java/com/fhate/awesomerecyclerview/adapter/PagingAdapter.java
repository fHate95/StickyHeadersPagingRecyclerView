package com.fhate.awesomerecyclerview.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhate.awesomerecyclerview.R;
import com.fhate.awesomerecyclerview.data.Item;
import com.fhate.awesomerecyclerview.decoration.HeaderItemDecoration;
import com.fhate.awesomerecyclerview.decoration.RecyclerSectionItemDecoration;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagingAdapter extends PagedListAdapter<Item, PagingAdapter.ViewHolder> implements RecyclerSectionItemDecoration.SectionCallback {
//        implements HeaderItemDecoration.StickyHeaderInterface {

    private Context context;
    private LayoutInflater inflater;

    AdapterClickListener clickListener;

    @Override
    public boolean isSection(int position) {
        return position == 0
                || getItem(position)
                .getName()
                .charAt(0) != getItem(position - 1)
                .getName()
                .charAt(0);
    }

    @Override
    public CharSequence getSectionHeader(int position) {
        return getItem(position)
                .getName()
                .subSequence(0,
                        1);
    }

//    @Override
//    public int getHeaderPositionForItem(int itemPosition) {
//        int headerPosition = 0;
//        do {
//            if (this.isHeader(itemPosition)) {
//                headerPosition = itemPosition;
//                break;
//            }
//            itemPosition -= 1;
//        } while (itemPosition >= 0);
//        return headerPosition;
//    }
//
//    @Override
//    public int getHeaderLayout(int headerPosition) {
//        if (getItem(headerPosition).getViewType() == Item.TYPE_SECTION) {
//            return R.layout.rv_section;
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public void bindHeaderData(View header, int headerPosition) {
//
//    }
//
//    @Override
//    public boolean isHeader(int itemPosition) {
//        return Objects.requireNonNull(getItem(itemPosition)).getViewType() == Item.TYPE_SECTION;
//    }

    public interface AdapterClickListener {
        void onItemClick(Item item);
    }

    public PagingAdapter(@NonNull DiffUtil.ItemCallback<Item> diffCallback, Context context,
                         AdapterClickListener clickListener) {
        super(diffCallback);
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Item.TYPE_ITEM:
                return new ItemViewHolder(inflater.inflate(R.layout.rv_item, parent, false));
            case Item.TYPE_SECTION:
                return new SectionViewHolder(inflater.inflate(R.layout.rv_section, parent, false));
            default:
                return new ItemViewHolder(inflater.inflate(R.layout.rv_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(getItem(position));
    }

    abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindItems(Item item);
    }

    class ItemViewHolder extends ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_view) LinearLayout itemView;
        @BindView(R.id.tv_name) TextView tvName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        void bindItems(Item item) {
            tvName.setText(item.getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_view:
                    clickListener.onItemClick(getItem(getAdapterPosition()));
                    break;
            }
        }
    }

    class SectionViewHolder extends ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_view) LinearLayout itemView;
        @BindView(R.id.tv_name) TextView tvName;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        void bindItems(Item item) {
            tvName.setText(item.getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_view:
                    clickListener.onItemClick(getItem(getAdapterPosition()));
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Objects.requireNonNull(getItem(position)).getViewType();
    }
}
