package com.app.filtar.uis.activity_home.explanation_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.filtar.R;
import com.app.filtar.adapter.BlogAdapter;
import com.app.filtar.adapter.BlogTagsAdapter;
import com.app.filtar.databinding.FragmentExplanationBinding;
import com.app.filtar.databinding.FragmentMoreBinding;
import com.app.filtar.model.BlogModel;
import com.app.filtar.model.BlogTagsModel;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.mvvm.FragmentExplanationMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_blogs_details.BlogDetailsActivity;
import com.app.filtar.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentExplanation extends BaseFragment {
    private static final String TAG = FragmentExplanation.class.getName();
    private HomeActivity activity;
    private FragmentExplanationBinding binding;
    // private FragmentHomeMvvm fragmentHomeMvvm;
    private GeneralMvvm generalMvvm;
    private FragmentExplanationMvvm mvvm;
    private BlogAdapter adapter;
    private List<BlogModel> blogModelList;
    private BlogTagsAdapter blogTagsAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explanation, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        blogModelList=new ArrayList<>();
        adapter=new BlogAdapter(blogModelList,activity,this);
        blogTagsAdapter=new BlogTagsAdapter(activity,this,getLang());
        mvvm = ViewModelProviders.of(this).get(FragmentExplanationMvvm.class);

        mvvm.getIsDataLoading().observe(activity, isLoading -> {

            binding.swipeRefresh.setRefreshing(isLoading);

        });
        mvvm.getOnDataSuccess().observe(activity, list -> {
            adapter.updateList(new ArrayList<>());

            if (list != null && list.size() > 0) {
                adapter.updateList(list);
                //         binding.cardNoData.setVisibility(View.GONE);
            } else {
                //       binding.cardNoData.setVisibility(View.VISIBLE);
            }
        });
        mvvm.getOnCategoryDataSuccess().observe(activity, new Observer<List<BlogTagsModel>>() {
            @Override
            public void onChanged(List<BlogTagsModel> blogTagsModels) {
                if (blogTagsModels != null && blogTagsModels.size() > 0) {
                    blogTagsModels.add(0,new BlogTagsModel(getResources().getString(R.string.all)));
                    BlogTagsModel blogTagsModel=blogTagsModels.get(0);
                    blogTagsModel.setSelected(true);
                    blogTagsModels.set(0,blogTagsModel);
                    blogTagsAdapter.updateList(blogTagsModels);
                    mvvm.getBlogs(blogTagsModel.getTitle());
                    //         binding.cardNoData.setVisibility(View.GONE);
                } else {
                    //       binding.cardNoData.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.recViewBlogs.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewBlogs.setAdapter(adapter);
        binding.recViewMain.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false));
        binding.recViewMain.setAdapter(blogTagsAdapter);
        mvvm.getCategory();
    }


    public void navigateToDetails(String id) {
        Intent intent=new Intent(activity, BlogDetailsActivity.class);
        intent.putExtra("data",id);
        startActivity(intent);
    }

    public void show(BlogTagsModel blogTagsModel) {
        mvvm.getBlogs(blogTagsModel.getTitle());
    }
}