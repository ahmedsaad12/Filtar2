package com.app.filtar.uis.activity_home.market_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.filtar.R;
import com.app.filtar.adapter.FilterProductAdapter;
import com.app.filtar.adapter.MainProductCategoryAdapter;
import com.app.filtar.databinding.FragmentMarketBinding;
import com.app.filtar.databinding.FragmentMoreBinding;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.model.ProductModel;
import com.app.filtar.mvvm.FragmentMarketMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_home.HomeActivity;
import com.app.filtar.uis.activity_product_details.ProductDetailsActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;


public class FragmentMarket extends BaseFragment {
    private static final String TAG = FragmentMarket.class.getName();
    private HomeActivity activity;
    private FragmentMarketBinding binding;
    private FragmentMarketMvvm fragmentMarketMvvm;
    private String priceMin, priceMax;
    private String query;
    private MainProductCategoryAdapter mainProductCategoryAdapter;
    private FilterProductAdapter productAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        fragmentMarketMvvm = ViewModelProviders.of(this).get(FragmentMarketMvvm.class);
        fragmentMarketMvvm.getIsLoading().observe(activity, isLoading -> {
            binding.tvNoData.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(isLoading);
        });

        fragmentMarketMvvm.getOnCategoryDataSuccess().observe(activity, list -> {
            binding.swipeRefresh.setRefreshing(false);


            if (list.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);
            }

            if (mainProductCategoryAdapter != null) {
                list.add(0,new CategoryModel(getResources().getString(R.string.all)));
                CategoryModel categoryModel = list.get(0);
                categoryModel.setSelected(true);
                categoryModel.setId("");
                list.set(0,categoryModel);

                Log.e("dlldl",categoryModel.getTitle());
                mainProductCategoryAdapter.updateList(list);
                fragmentMarketMvvm.getCategoryId().setValue(list.get(0).getId());
                fragmentMarketMvvm.searchProduct(query, priceMin, priceMax, "");

            }
        });
        fragmentMarketMvvm.getOnProductsDataSuccess().observe(activity, list -> {
            if (list.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);

            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            productAdapter.updateList(list);
        });

        binding.imageFilter.setOnClickListener(view -> openSheet());
        binding.imageClose.setOnClickListener(view -> closeSheet());
        binding.btnShowFilterResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceMin = binding.edtMin.getText().toString();
                priceMax = binding.edtMax.getText().toString();
                fragmentMarketMvvm.searchProduct(fragmentMarketMvvm.getQuery().getValue(), priceMin, priceMax, null);
                closeSheet();

            }
        });
        fragmentMarketMvvm.getCategory();
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentMarketMvvm.searchProduct(fragmentMarketMvvm.getQuery().getValue(), priceMin, priceMax, null);

            }
        });
        mainProductCategoryAdapter = new MainProductCategoryAdapter(activity, this, getLang());
        binding.recViewMain.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewMain.setAdapter(mainProductCategoryAdapter);


        productAdapter = new FilterProductAdapter(activity, this, getLang());
        binding.recViewProducts.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recViewProducts.setAdapter(productAdapter);

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    binding.edtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            emitter.onNext(s.toString().trim());
                        }
                    });
                }).debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .subscribe(query -> {
                    fragmentMarketMvvm.getQuery().postValue(query);
                    fragmentMarketMvvm.searchProduct(query, priceMin, priceMax, null);
                });
        fragmentMarketMvvm.getCategory();

    }

    private void openSheet() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_up);


        binding.llFilter.clearAnimation();
        binding.llFilter.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                binding.llFilter.setVisibility(View.VISIBLE);


            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void closeSheet() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);


        binding.llFilter.clearAnimation();
        binding.llFilter.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                binding.llFilter.setVisibility(View.GONE);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void getProduct(CategoryModel categoryModel) {
        fragmentMarketMvvm.setCategoryId(categoryModel.getId(), activity, getUserModel());
        fragmentMarketMvvm.searchProduct(fragmentMarketMvvm.getQuery().getValue(), priceMin, priceMax, null);

    }

    public void showProductDetails(ProductModel productModel) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("id", productModel.getId());
        startActivity(intent);
    }
}