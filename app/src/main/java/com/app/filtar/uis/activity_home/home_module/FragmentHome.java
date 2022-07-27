package com.app.filtar.uis.activity_home.home_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.filtar.R;
import com.app.filtar.adapter.RecentProductAdapter;
import com.app.filtar.adapter.SliderAdapter;
import com.app.filtar.databinding.FragmentHomeBinding;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.SliderDataModel;
import com.app.filtar.mvvm.FragmentHomeMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_add_filter.AddFilterActivity;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private GeneralMvvm generalMvvm;
    private FragmentHomeMvvm fragmentHomeMvvm;
    private SliderAdapter sliderAdapter;
    private List<SliderDataModel.SliderModel> sliderModelList;
    private Timer timer;
    private RecentProductAdapter recentProductAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        sliderModelList=new ArrayList<>();
        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        fragmentHomeMvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loaderSlider.setVisibility(View.VISIBLE);

            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        fragmentHomeMvvm.getIsLoadingRecentProduct().observe(activity, isLoading -> {
            if (!isLoading) {
                binding.loaderMostSales.stopShimmer();
                binding.loaderMostSales.setVisibility(View.GONE);
            }
        });
        fragmentHomeMvvm.getSliderDataModelMutableLiveData().observe(activity, new androidx.lifecycle.Observer<SliderDataModel>() {
            @Override
            public void onChanged(SliderDataModel sliderDataModel) {

                if (sliderDataModel.getData() != null) {
                    binding.loaderSlider.setVisibility(View.GONE);
                    sliderModelList.clear();
                    sliderModelList.addAll(sliderDataModel.getData());
                    sliderAdapter.notifyDataSetChanged();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTask(), 3000, 3000);
                }

            }
        });
        fragmentHomeMvvm.getOnRecentProductDataModel().observe(activity, list -> {
            binding.swipeRefresh.setRefreshing(false);
            if (list.size() > 0) {
                binding.tvNoMostRecentProduct.setVisibility(View.GONE);
            } else {
                binding.tvNoMostRecentProduct.setVisibility(View.VISIBLE);
            }
            if (recentProductAdapter != null) {
                recentProductAdapter.updateList(list);

            }

        });

        sliderAdapter = new SliderAdapter(sliderModelList, activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPadding(80, 0, 80, 0);
        binding.pager.setPageMargin(20);
        fragmentHomeMvvm.getSlider();
        recentProductAdapter = new RecentProductAdapter(activity, this, getLang());
        binding.recViewMostSaleProducts.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewMostSaleProducts.setAdapter(recentProductAdapter);
        fragmentHomeMvvm.getRecentProduct();

        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
generalMvvm.getAllAppoinmentModelMutableLiveData().observe(activity, new Observer<AllAppoinmentModel>() {
    @Override
    public void onChanged(AllAppoinmentModel allAppoinmentModel) {
        if(allAppoinmentModel!=null){
            binding.setModel(allAppoinmentModel.getData().get(0));
        }
    }
});
        binding.setLang(getLang());
        binding.imFiltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getUserModel() == null) {
                    generalMvvm.getOrderpage().setValue(5);
                } else {
                    Intent intent = new Intent(activity, AddFilterActivity.class);
                    startActivity(intent);
                }
            }
        });
        generalMvvm.getfirstCleaningTime(getUserModel());
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }

    }
}