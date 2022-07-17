package com.filtar.uis.activity_home.home_module;

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

import com.filtar.R;
import com.filtar.databinding.FragmentHomeBinding;
import com.filtar.mvvm.GeneralMvvm;
import com.filtar.uis.activity_add_filter.AddFilterActivity;
import com.filtar.uis.activity_base.BaseFragment;
import com.filtar.uis.activity_home.HomeActivity;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
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
    }


}