package com.app.filtar.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.app.filtar.R;
import com.app.filtar.databinding.ProductRowBinding;
import com.app.filtar.model.ProductModel;
import com.app.filtar.uis.activity_home.market_module.FragmentMarket;

import java.util.List;

public class FilterProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;


    public FilterProductAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.product_row, parent, false);
        return new MyHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ProductModel productModel = list.get(position);

        myHolder.binding.setModel(productModel);

        Spannable word = new SpannableString(list.get(position).getPrice());

        word.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myHolder.binding.tvPrice.setText(word);
        Spannable wordTwo = new SpannableString(context.getResources().getString(R.string.egp));

        wordTwo.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.black)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myHolder.binding.tvPrice.append(wordTwo);




        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentMarket) {
                FragmentMarket fragmentMarket = (FragmentMarket) fragment;
                fragmentMarket.showProductDetails(list.get(holder.getAdapterPosition()));
            }
        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public ProductRowBinding binding;

        public MyHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
