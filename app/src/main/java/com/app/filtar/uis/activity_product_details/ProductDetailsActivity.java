package com.app.filtar.uis.activity_product_details;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.app.filtar.R;
import com.app.filtar.adapter.ImagesSliderAdapter;
import com.app.filtar.databinding.ActivityProductDetailsBinding;
import com.app.filtar.model.ProductModel;
import com.app.filtar.mvvm.ActivityProductDetailsMvvm;
import com.app.filtar.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProductDetailsActivity extends BaseActivity {
    private ActivityProductDetailsBinding binding;
    private ActivityProductDetailsMvvm mvvm;
    private ImagesSliderAdapter sliderAdapter;
    private Timer timer;
    private List<ProductModel.Images> imagesList;
    private String id;
    private int amount = 1;
    private ProductModel product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.notifications), R.color.white, R.color.black);

        imagesList = new ArrayList<>();
        binding.setLang(getLang());
        mvvm = ViewModelProviders.of(this).get(ActivityProductDetailsMvvm.class);

        mvvm.getIsDataLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.progBar.setVisibility(View.VISIBLE);
                // binding.progBarSlider.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);
//                binding.progBarSlider.setVisibility(View.GONE);
            }
        });
        mvvm.getOnDataSuccess().observe(this, product -> {
            if (product != null) {
                this.product = product;
                binding.scrollView.setVisibility(View.VISIBLE);
                binding.flCart.setVisibility(View.VISIBLE);
                binding.setModel(product);
                Spannable word = new SpannableString(product.getPrice());

                word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                binding.tvPrice.setText(word);
                Spannable wordTwo = new SpannableString(getResources().getString(R.string.egp));

                wordTwo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                binding.tvPrice.append(wordTwo);
                imagesList.clear();
                if (product.getImages().size() > 0) {
                    imagesList.addAll(product.getImages());
                    sliderAdapter.notifyDataSetChanged();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTask(), 3000, 3000);
                    binding.fl.setVisibility(View.VISIBLE);
                    binding.image.setVisibility(View.GONE);
                    binding.pagerProduct.setVisibility(View.VISIBLE);
                } else {
                    binding.fl.setVisibility(View.GONE);
                    binding.image.setVisibility(View.VISIBLE);
                    binding.pagerProduct.setVisibility(View.GONE);
                }
            }
        });
        mvvm.getSingleProduct(id, getLang());
        sliderAdapter = new ImagesSliderAdapter(imagesList, this);
        binding.pagerProduct.setAdapter(sliderAdapter);
        binding.pagerProduct.setClipToPadding(false);
        // binding.pagerProduct.setPadding(80, 0, 80, 0);
        //binding.pagerProduct.setPageMargin(20);
        binding.tab.setViewPager(binding.pagerProduct);

        binding.flCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public class MyTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(() -> {
                int current_page = binding.pagerProduct.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pagerProduct.setCurrentItem(binding.pagerProduct.getCurrentItem() + 1);
                } else {
                    binding.pagerProduct.setCurrentItem(0);

                }
            });

        }

    }
    public void addProductToCart(ProductModel productModel) {


    }

}