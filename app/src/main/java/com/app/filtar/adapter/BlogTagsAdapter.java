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
import com.app.filtar.databinding.BlogTagsRowBinding;
import com.app.filtar.databinding.MainCategoryRowBinding;
import com.app.filtar.model.BlogTagsModel;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.uis.activity_home.explanation_module.FragmentExplanation;
import com.app.filtar.uis.activity_home.market_module.FragmentMarket;

import java.util.List;

public class BlogTagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BlogTagsModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;
    private int currentPos = 0;
    private MyHolder oldHolder;

    public BlogTagsAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        this.lang = lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BlogTagsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.blog_tags_row, parent, false);
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

            BlogTagsModel category = list.get(myHolder.getAdapterPosition());

            if (!category.isSelected()) {

                if (oldHolder != null) {

                    BlogTagsModel oldCategory = list.get(currentPos);
                    oldCategory.setSelected(false);
                    list.set(currentPos, oldCategory);
                    oldHolder.binding.setModel(oldCategory);


                }
                category.setSelected(true);
                list.set(myHolder.getAdapterPosition(), category);
                myHolder.binding.setModel(category);


                currentPos = myHolder.getAdapterPosition();
                oldHolder = myHolder;
                if (fragment instanceof FragmentExplanation) {
                    FragmentExplanation fragmentProducts = (FragmentExplanation) fragment;
                    fragmentProducts.show(list.get(myHolder.getAdapterPosition()));
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public BlogTagsRowBinding binding;

        public MyHolder(BlogTagsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<BlogTagsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedPos(int pos) {
        currentPos = pos;
    }


}
