package com.app.filtar.uis.activity_home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.app.filtar.R;
import com.app.filtar.adapter.MyPagerAdapter;
import com.app.filtar.back_service.backService;
import com.app.filtar.databinding.ActivityHomeBinding;
import com.app.filtar.model.FilterCandleModel;
import com.app.filtar.model.StatusResponse;
import com.app.filtar.model.UserModel;
import com.app.filtar.mvvm.ActivityHomeMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.preferences.Preferences;
import com.app.filtar.uis.activity_add_filter.AddFilterActivity;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_cart.CartActivity;
import com.app.filtar.uis.activity_home.main_module.FragmentMain;
import com.app.filtar.uis.activity_login.LoginActivity;
import com.app.filtar.uis.activity_notification.NotificationActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class HomeActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private ActivityHomeBinding binding;
    private ActivityHomeMvvm homeActivityMvvm;
    private Stack<Integer> stack;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments;
    private UserModel userModel;
    private GeneralMvvm generalMvvm;
    private String notificationMsg, notificationTitle;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private TextView title1;
    private String candel;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.getStringExtra("NotificationMsg") != null) {
            notificationMsg = intent.getStringExtra("NotificationMsg");
            notificationTitle = intent.getStringExtra("notificationTitle");
            if (intent.getStringExtra("candel_num") != null) {
                day = intent.getStringExtra("day");
                showDialog2(intent.getStringExtra("candel_num"));

            } else {
                showDialog();
            }
        }
    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        generalMvvm.getOrderpage().observe(this, this::setItemPos);
        if (Preferences.getInstance().getCart(this) != null) {
            binding.setCount(Preferences.getInstance().getCart(this).getDetails().size() + "");
        }
        generalMvvm.getCart().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 0) {
                    binding.setCount(integer + "");
                }
            }
        });
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
        homeActivityMvvm.getOnLoggedOutSuccess().observe(this, loggedOut -> {
            if (loggedOut) {
                setUserModel(null);
                navigationToLoginActivity();
            }
        });
        homeActivityMvvm.getOnUpdateSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_SHORT).show();
                }
            }
        });
        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                userModel = getUserModel();
                userModel.setFirebase_token(token);
                setUserModel(userModel);
            }
        });
        binding.flNotification.setOnClickListener(v -> {
            if (getUserModel() != null) {
                //
                //   binding.setCount("0");
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
            } else {
                navigationToLoginActivity();
            }
        });
        binding.flCart.setOnClickListener(v -> {

            //
            //   binding.setCount("0");
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);

        });

        if (userModel != null) {
            homeActivityMvvm.updateFirebase(this, userModel);
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        if (getUserModel().getData().getWater_type() != null) {
            Intent intent = new Intent(this, backService.class);
            intent.putExtra("data", getUserModel());
            startService(intent);
        }
        createDateDialog();
    }


    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog = DatePickerDialog.newInstance(HomeActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.grey4));
        datePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        datePickerDialog.setMaxDate(calendar);
        //  datePickerDialog.onDayOfMonthSelected(2022,8,1);
        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);

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

    public void showDialog2(String candel) {
        this.candel = candel;
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
                .setPositiveButton("تأكيد",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                // homeActivityMvvm.editFilter(getUserModel(), candel, date,this);
                                homeActivityMvvm.editFilter(getUserModel(), candel, day, HomeActivity.this);

                                //finishAffinity();
                            }
                        }).setNegativeButton("موعد اخر",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                datePickerDialog.show(getSupportFragmentManager(), "");
                                //finishAffinity();
                            }
                        });
        builder.show();
    }

    private void navigationToLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        homeActivityMvvm.editFilter(getUserModel(), candel, date, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Preferences.getInstance().getCart(this) != null) {
            binding.setCount(Preferences.getInstance().getCart(this).getDetails().size() + "");
        }
    }

    public void logout() {
        homeActivityMvvm.logout(getUserModel(), this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessage(StatusResponse messageModel) {
       if(messageModel.getStatus()==200){
           generalMvvm.getFilter().postValue(1);
       }

    }
}
