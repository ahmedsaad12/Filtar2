package com.app.filtar.uis.activity_home.more_module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.filtar.R;
import com.app.filtar.databinding.FragmentHomeBinding;
import com.app.filtar.databinding.FragmentMoreBinding;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_home.HomeActivity;


public class FragmentMore extends BaseFragment {
    private static final String TAG = FragmentMore.class.getName();
    private HomeActivity activity;
    private FragmentMoreBinding binding;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
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
        binding.llRateApp.setOnClickListener(v -> {
            rateApp();
        });
        binding.llShareApp.setOnClickListener(v -> {
            share();
        });

        binding.imageFacebook.setOnClickListener(v -> {

//            if (setting != null && setting.getFacebook() != null) {
//                String url = setting.getFacebook();
//                createIntentForSocial(url);
//            } else {
//                Toast.makeText(activity, R.string.inv_link, Toast.LENGTH_SHORT).show();
//            }
        });

        binding.imageTwitter.setOnClickListener(v -> {
//
//            if (setting != null && setting.getTwitter() != null) {
//                String url = setting.getTwitter();
//                createIntentForSocial(url);
//            } else {
//                Toast.makeText(activity, R.string.inv_link, Toast.LENGTH_SHORT).show();
//            }
        });

        binding.imageInstagram.setOnClickListener(v -> {

//            if (setting != null && setting.getInsta() != null) {
//                String url = setting.getInsta();
//                createIntentForSocial(url);
//            } else {
//                Toast.makeText(activity, R.string.inv_link, Toast.LENGTH_SHORT).show();
//            }
        });

        binding.imageSnapChat.setOnClickListener(v -> {

//            if (setting != null && setting.getSnapchat() != null) {
//                String url = setting.getSnapchat();
//                createIntentForSocial(url);
//            } else {
//                Toast.makeText(activity, R.string.inv_link, Toast.LENGTH_SHORT).show();
//            }
        });


    }

    private void createIntentForSocial(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void share() {
        String app_url = "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
        String content = getString(R.string.app_name) + "\n" + app_url;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(intent, "Share"));

    }

    private void rateApp() {
        final String appPackageName = activity.getPackageName();
        try {
            Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            appStoreIntent.setPackage("com.android.vending");

            startActivity(appStoreIntent);
        } catch (android.content.ActivityNotFoundException exception) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


}