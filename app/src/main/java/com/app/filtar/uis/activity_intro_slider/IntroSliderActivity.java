package com.app.filtar.uis.activity_intro_slider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.app.filtar.R;
import com.app.filtar.adapter.MyPagerAdapter;
import com.app.filtar.databinding.ActivityIntroSliderBinding;
import com.app.filtar.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class IntroSliderActivity extends BaseActivity {
    private ActivityIntroSliderBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_slider);
        initView();

    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro1, getString(R.string.hey_you),getString(R.string.in_filter_app), getString(R.string.first_app), 1));
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro2, getString(R.string.know_you),getString(R.string.busy), getString(R.string.we_think), 2));
        fragmentList.add(IntroFragment.newInstance(R.drawable.intro3, getString(R.string.market),getString(R.string.buy_online), getString(R.string.shop_buy), 3));

        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, new ArrayList<>());
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        binding.pager.setAdapter(adapter);
        binding.tab.setViewPager(binding.pager);

    }

    private void navigateToLoginActivity() {
//        UserSettingsModel model = getUserSettings();
//        model.setFirstTime(false);
//        setUserSettings(model);
//        setResult(RESULT_OK);
//        finish();
    }

    public void shownextpage(int pagenum) {
        binding.pager.setCurrentItem(pagenum);
    }
}