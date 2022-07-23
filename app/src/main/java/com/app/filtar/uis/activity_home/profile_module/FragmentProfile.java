package com.app.filtar.uis.activity_home.profile_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.filtar.R;
import com.app.filtar.databinding.FragmentMoreBinding;
import com.app.filtar.databinding.FragmentProfileBinding;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_add_filter.AddFilterActivity;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_contact_us.ContactUsActivity;
import com.app.filtar.uis.activity_home.HomeActivity;
import com.app.filtar.uis.activity_myfilter.MyFilterActivity;
import com.app.filtar.uis.activity_sign_up.SignUpUserActivity;


public class FragmentProfile extends BaseFragment {
    private static final String TAG = FragmentProfile.class.getName();
    private HomeActivity activity;
    private FragmentProfileBinding binding;
    // private FragmentHomeMvvm fragmentHomeMvvm;
    private GeneralMvvm generalMvvm;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        // fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
binding.setModel(getUserModel());
        binding.setLang(getLang());
        binding.profileLayout.setLang(getLang());
        binding.profileLayout.carViewContactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ContactUsActivity.class);
                startActivity(intent);
            }
        });
        binding.profileLayout.cardMyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, MyFilterActivity.class);
                startActivity(intent);
            }
        });
        binding.profileLayout.cardFilterSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AddFilterActivity.class);
                startActivity(intent);
            }
        });
        binding.profileLayout.carEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, SignUpUserActivity.class);
                startActivity(intent);
            }
        });
    }


}