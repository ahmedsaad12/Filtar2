package com.app.filtar.uis.activity_intro_slider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.app.filtar.R;
import com.app.filtar.databinding.FragmentIntroBinding;
import com.app.filtar.model.UserSettingsModel;
import com.app.filtar.uis.activity_base.BaseFragment;


public class IntroFragment extends BaseFragment {
    private FragmentIntroBinding binding;
    private String title, content;
    private int imageResource;
    private IntroSliderActivity activity;
    private String title2;
    private int pagenum;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (IntroSliderActivity) context;
    }

    public static IntroFragment newInstance(int imageResource, String title,String title2, String content, int  pagenum) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt("img", imageResource);
        args.putString("title", title);
        args.putString("title2", title2);
        args.putString("content", content);
        args.putInt("pagenum", pagenum);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResource = getArguments().getInt("img");
            title = getArguments().getString("title");
            title2 = getArguments().getString("title2");
            content = getArguments().getString("content");
            pagenum = getArguments().getInt("pagenum");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false);
        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.image.setImageResource(imageResource);
        binding.tvTitle1.setText(title);
        binding.tvTitle.setText(title2);
        binding.tvContent.setText(content);


        binding.btnStart.setOnClickListener(v -> {
            if(pagenum==3){
            navigateToLoginActivity();}
            else {
                activity.shownextpage(pagenum);
            }
        });
        binding.btnSkip.setOnClickListener(v -> navigateToLoginActivity());

    }

    private void navigateToLoginActivity() {

        UserSettingsModel model = new UserSettingsModel();
        model.setFirstTime(false);
        setUserSettings(model);
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}