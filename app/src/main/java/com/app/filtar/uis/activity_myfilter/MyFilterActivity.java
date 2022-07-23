package com.app.filtar.uis.activity_myfilter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.filtar.R;
import com.app.filtar.adapter.AppoinmentAdapter;
import com.app.filtar.databinding.ActivityMyFilterBinding;
import com.app.filtar.model.AddFlterModel;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.FilterCandleModel;
import com.app.filtar.mvvm.ActivityAddFilterMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_add_filter.AddFilterActivity;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyFilterActivity extends BaseActivity {
    private ActivityMyFilterBinding binding;
    private GeneralMvvm generalMvvm;
    private AppoinmentAdapter appoinmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_filter);
        initView();
    }


    private void initView() {
        appoinmentAdapter = new AppoinmentAdapter(this, getLang());
        binding.recViewMostSaleProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewMostSaleProducts.setAdapter(appoinmentAdapter);
        String title = getString(R.string.my_filter);
        setUpToolbar(binding.toolbar, title, R.color.white, R.color.black);
        binding.setLang(getLang());
        binding.swipeRefresh.setRefreshing(true);
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        generalMvvm.getAllAppoinmentModelMutableLiveData().observe(this, new Observer<AllAppoinmentModel>() {
            @Override
            public void onChanged(AllAppoinmentModel allAppoinmentModel) {
                if (allAppoinmentModel != null) {
                    binding.swipeRefresh.setRefreshing(false);
                    binding.setModel(allAppoinmentModel.getData().get(0));
                    appoinmentAdapter.updateList(allAppoinmentModel.getData());
                }
            }
        });
        binding.setLang(getLang());

        generalMvvm.getfirstCleaningTime(getUserModel());
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                generalMvvm.getfirstCleaningTime(getUserModel());
            }
        });

    }


}