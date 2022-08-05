package com.app.filtar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.filtar.R;
import com.app.filtar.databinding.MainCategoryRowBinding;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.uis.activity_home.market_module.FragmentMarket;
import com.app.filtar.uis.activity_market_detials.MarketDetialsActivity;

import java.util.List;

public class MainProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;
    private int currentPos = 0;
    private MyHolder oldHolder;

    public MainProductCategoryAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        this.lang = lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setLang(lang);

        if (oldHolder == null) {
            if (currentPos == position) {
                oldHolder = myHolder;
            }
        }


        myHolder.itemView.setOnClickListener(v -> {

            CategoryModel category = list.get(myHolder.getAdapterPosition());

            if (!category.isSelected()) {

                if (oldHolder != null) {

                    CategoryModel oldCategory = list.get(currentPos);
                    oldCategory.setSelected(false);
                    list.set(currentPos, oldCategory);
                    oldHolder.binding.setModel(oldCategory);


                }
                category.setSelected(true);
                list.set(myHolder.getAdapterPosition(), category);
                myHolder.binding.setModel(category);


                currentPos = myHolder.getAdapterPosition();
                oldHolder = myHolder;
                if (fragment instanceof FragmentMarket) {
                    FragmentMarket fragmentProducts = (FragmentMarket) fragment;
                    fragmentProducts.getProduct(list.get(myHolder.getAdapterPosition()));
                }
                else if (fragment instanceof com.app.filtar.uis.activity_home_provider.market_module.FragmentMarket) {
                    com.app.filtar.uis.activity_home_provider.market_module.FragmentMarket fragmentProducts = (com.app.filtar.uis.activity_home_provider.market_module.FragmentMarket)fragment;
                    fragmentProducts.getProduct(list.get(myHolder.getAdapterPosition()));
                }else if (context instanceof MarketDetialsActivity) {
                    MarketDetialsActivity fragmentMarket = (MarketDetialsActivity) context;
                    fragmentMarket.getProduct(list.get(holder.getAdapterPosition()));
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public MainCategoryRowBinding binding;

        public MyHolder(MainCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedPos(int pos) {
        currentPos = pos;
    }


}
