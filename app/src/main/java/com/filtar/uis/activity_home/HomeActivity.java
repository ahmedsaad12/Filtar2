package com.filtar.uis.activity_home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;


import com.filtar.R;
import com.filtar.adapter.MyPagerAdapter;
import com.filtar.databinding.ActivityHomeBinding;
import com.filtar.model.UserModel;
import com.filtar.mvvm.ActivityHomeMvvm;
import com.filtar.mvvm.GeneralMvvm;
import com.filtar.uis.activity_base.BaseActivity;
import com.filtar.uis.activity_home.main_module.FragmentMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HomeActivity extends BaseActivity  {
    private ActivityHomeBinding binding;
    private ActivityHomeMvvm homeActivityMvvm;
    private Stack<Integer> stack;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments;
    private UserModel userModel;
    private GeneralMvvm generalMvvm;
    private String notificationMsg, notificationTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if(intent.getStringExtra("NotificationMsg")!=null) {
            notificationMsg = intent.getStringExtra("NotificationMsg");
            notificationTitle = intent.getStringExtra("notificationTitle");
            Log.e("uuuuu",notificationMsg);
            showDialog();
        }
    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        generalMvvm.getOrderpage().observe(this, this::setItemPos);
        userModel = getUserModel();
        fragments = new ArrayList<>();
        stack = new Stack<>();
        if (stack.isEmpty()) {
            stack.push(0);

        }



        fragments.add(FragmentMain.newInstance());
       //// fragments.add(Fragmentlogin.newInstance());
        //fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_profile, R.id.navHostFragmentProfile));

        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, null);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());

       // binding.bottomNavigationView.setOnItemSelectedListener(this);

        homeActivityMvvm = ViewModelProviders.of(this).get(ActivityHomeMvvm.class);


        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                userModel = getUserModel();
                userModel.setFirebase_token(token);
                setUserModel(userModel);
            }
        });


        if (userModel != null) {
            homeActivityMvvm.updateFirebase(this, userModel);
//            if (!EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().register(this);
//            }
        }
    }




//    public void updateFirebase() {
//        if (getUserModel() != null) {
//             homeActivityMvvm.updateFirebase(this, getUserModel());
//        }
//    }



    private void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
        stack.push(pos);
    }




    @Override
    public void onBackPressed() {
        if (stack.size() > 1) {
            stack.pop();
            binding.pager.setCurrentItem(stack.peek());
        } else {
            FragmentMain fragmentHome = (FragmentMain) adapter.getItem(0);
            if (!fragmentHome.onBackPress()) {
                super.onBackPressed();
            }

        }

    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("\n" + notificationMsg);
        TextView title = new TextView(HomeActivity.this);
        title.setText(notificationTitle);
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(20, 20, 20, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        builder.setCustomTitle(title)
                .setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                //finishAffinity();
                            }
                        });
        builder.show();
    }

}
