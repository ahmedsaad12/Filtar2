package com.app.filtar.uis.activity_notification;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.filtar.R;
import com.app.filtar.adapter.NotificationAdapter;
import com.app.filtar.databinding.ActivityNotificationBinding;
import com.app.filtar.mvvm.ActivityNotificationMvvm;
import com.app.filtar.uis.activity_base.BaseActivity;


public class NotificationActivity extends BaseActivity {

    private ActivityNotificationBinding binding;
    private NotificationAdapter adapter;
    private ActivityNotificationMvvm activityNotificationMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initView();


    }


    private void initView() {
        activityNotificationMvvm = ViewModelProviders.of(this).get(ActivityNotificationMvvm.class);
        activityNotificationMvvm.getIsLoading().observe(this, loading -> {
            binding.swipeRefresh.setRefreshing(loading);

        });
        activityNotificationMvvm.getNotification().observe(this, list -> {
            if (list.size() > 0) {
                adapter.updateList(list);
                binding.cardNoData.setVisibility(View.GONE);
            } else {
                binding.cardNoData.setVisibility(View.VISIBLE);

            }
        });
        setUpToolbar(binding.toolbar, getString(R.string.notifications), R.color.white, R.color.black);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this);
        binding.recView.setAdapter(adapter);
        activityNotificationMvvm.getNotifications(getUserModel(),getLang());

        binding.swipeRefresh.setOnRefreshListener(() -> activityNotificationMvvm.getNotifications(getUserModel(),getLang()));


//        EventBus.getDefault().register(this);

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onNewNotificationListener(NotModel model) {
//        activityNotificationMvvm.getNotifications(getUserModel());
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }
}