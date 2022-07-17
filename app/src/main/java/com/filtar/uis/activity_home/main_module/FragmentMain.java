package com.filtar.uis.activity_home.main_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;


import com.filtar.R;
import com.filtar.adapter.MyPagerAdapter;
import com.filtar.databinding.FragmentHomeBinding;
import com.filtar.databinding.FragmentMainBinding;
import com.filtar.mvvm.GeneralMvvm;
import com.filtar.uis.FragmentBaseNavigation;
import com.filtar.uis.activity_base.BaseFragment;
import com.filtar.uis.activity_home.HomeActivity;
import com.filtar.uis.activity_home.home_module.FragmentHome;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class FragmentMain extends BaseFragment implements ViewPager.OnPageChangeListener, NavigationBarView.OnItemSelectedListener {
    private GeneralMvvm generalMvvm;
    private HomeActivity activity;
    private FragmentMainBinding binding;
    private Stack<Integer> stack;
    private Map<Integer, Integer> map;
    private List<Fragment> fragments;
    private MyPagerAdapter adapter;

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        generalMvvm.getOrderpage().observe(activity, this::setItemPos);
        generalMvvm.getOnUserLoggedIn().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setItemPos(0);
            }
        });
        fragments = new ArrayList<>();
        stack = new Stack<>();
        map = new HashMap<>();
        if (stack.isEmpty()) {
            stack.push(0);

        }

        map.put(0, R.id.profile);
        map.put(1, R.id.market);
        map.put(2, R.id.explanations);
        map.put(3, R.id.more);


        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_profile, R.id.navHostFragmentProfile));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_market, R.id.navHostFragmentMarket));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_explanation, R.id.navHostFragmentexplanation));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_more, R.id.navHostFragmentMore));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_home, R.id.navHostFragmentHome));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_login, R.id.navHostFragmentLogin));

        adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, null);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());
        binding.pager.addOnPageChangeListener(this);
        binding.bottomNavigationView.setOnItemSelectedListener(this);
        if(getUserModel()==null){
            setItemPos(3);
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setItemPos(4);
            }
        });
        //  homeActivityMvvm = ViewModelProviders.of(this).get(ActivityHomeMvvm.class);


//        homeActivityMvvm.firebase.observe(this, token -> {
//            if (getUserModel() != null) {
//                userModel = getUserModel();
//                userModel.getData().setFirebase_token(token);
//                setUserModel(userModel);
//            }
//        });


//        if (userModel != null) {
//            homeActivityMvvm.updateFirebase(this, userModel);
//            if (!EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().register(this);
//            }
//        }
    }




//    public void updateFirebase() {
//        if (getUserModel() != null) {
//             homeActivityMvvm.updateFirebase(this, getUserModel());
//        }
//    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position<=3){
        int itemId = map.get(position);
        if (itemId != binding.bottomNavigationView.getSelectedItemId()) {
            binding.bottomNavigationView.setSelectedItemId(itemId);
        }
    }}

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemPos = getItemPos(item.getItemId());
        if (itemPos != binding.pager.getCurrentItem()) {
            setItemPos(itemPos);
        }
        return true;
    }

    private void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
        stack.push(pos);
    }

    private int getItemPos(int item_id) {
        for (int pos : map.keySet()) {
            if (map.get(pos) == item_id) {
                return pos;
            }
        }
        return 0;
    }
    public boolean onBackPress() {
        if (stack.size() > 1) {
            stack.pop();
            binding.pager.setCurrentItem(stack.peek());
            return true;
        } else {
            return false;
        }

    }

}