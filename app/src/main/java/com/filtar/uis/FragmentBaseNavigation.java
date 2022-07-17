package com.filtar.uis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.filtar.uis.activity_base.BaseFragment;


public class FragmentBaseNavigation extends BaseFragment {
    private int layoutResourceId;
    private int navHostId;
    private int defaultValue = -1;
    private View view;
    private NavController navController;
    private AppCompatActivity activity;


    public static FragmentBaseNavigation newInstance(int layoutResourceId, int navHostId) {
        Bundle bundle = new Bundle();
        bundle.putInt("layoutResourceId", layoutResourceId);
        bundle.putInt("navHostId", navHostId);

        FragmentBaseNavigation fragment = new FragmentBaseNavigation();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            layoutResourceId = bundle.getInt("layoutResourceId", defaultValue);
            navHostId = bundle.getInt("navHostId", defaultValue);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutResourceId != defaultValue) {
            view = inflater.inflate(layoutResourceId, container, false);

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (layoutResourceId != defaultValue && navHostId != defaultValue) {
            navController = Navigation.findNavController(activity, navHostId);


        }
    }

    public boolean onBackPress() {
        return navController.navigateUp();
    }
}
