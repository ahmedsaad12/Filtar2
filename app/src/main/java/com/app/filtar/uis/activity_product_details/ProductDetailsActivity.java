package com.app.filtar.uis.activity_product_details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.filtar.R;
import com.app.filtar.adapter.ImagesSliderAdapter;
import com.app.filtar.adapter.RecentProductAdapter;
import com.app.filtar.databinding.ActivityProductDetailsBinding;
import com.app.filtar.model.ProductModel;
import com.app.filtar.mvvm.ActivityProductDetailsMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.preferences.Preferences;
import com.app.filtar.uis.activity_add_new_product.AddNewProductActivity;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_market_detials.MarketDetialsActivity;

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
    private RecentProductAdapter productAdapter;
    private GeneralMvvm generalMvvm;
    private ActivityResultLauncher<Intent> launcher;

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
        setUpToolbar(binding.toolbar, getString(R.string.product_detials), R.color.white, R.color.black);
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        imagesList = new ArrayList<>();
        binding.setLang(getLang());
        mvvm = ViewModelProviders.of(this).get(ActivityProductDetailsMvvm.class);
        if (getUserModel().getData().getStore_name() != null) {
            binding.flMarket.setVisibility(View.GONE);
            binding.llMostSale.setVisibility(View.GONE);
            binding.flCart.setVisibility(View.GONE);
        }

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

if(result.getResultCode()==RESULT_OK){
    mvvm.getSingleProduct(id, getLang());

}

        });
        mvvm.getOnStatusSuccess().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                   setResult(RESULT_OK);
                   finish();
                }
            }
        });
        mvvm.getIsDataLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.progBar.setVisibility(View.VISIBLE);
                // binding.progBarSlider.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);
//                binding.progBarSlider.setVisibility(View.GONE);
            }
        });
        mvvm.getOnProductsDataSuccess().observe(this, list -> {
            binding.loaderMostSales.stopShimmer();
            binding.loaderMostSales.setVisibility(View.GONE);
            binding.recViewMostSaleProducts.setVisibility(View.VISIBLE);
            productAdapter.updateList(list);
            if (list.size() > 0) {
                binding.tvNoMostSaleProduct.setVisibility(View.GONE);
            } else {
                binding.tvNoMostSaleProduct.setVisibility(View.VISIBLE);
            }
        });

        mvvm.getOnDataSuccess().observe(this, product -> {
            if (product != null) {
                this.product = product;

                binding.scrollView.setVisibility(View.VISIBLE);
                // binding.flCart.setVisibility(View.VISIBLE);
                binding.setModel(product);
                if (getUserModel().getData().getStore_name() != null) {
                    if (!product.getProvider_id().equals(getUserModel().getData().getId())) {
                        binding.llmarket.setVisibility(View.GONE);
                    }
                }
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
                mvvm.searchProduct(product.getProvider_id());
            }
        });
        mvvm.getSingleProduct(id, getLang());
        sliderAdapter = new ImagesSliderAdapter(imagesList, this);
        binding.pagerProduct.setAdapter(sliderAdapter);
        binding.pagerProduct.setClipToPadding(false);
        // binding.pagerProduct.setPadding(80, 0, 80, 0);
        //binding.pagerProduct.setPageMargin(20);
        binding.tab.setViewPager(binding.pagerProduct);
        productAdapter = new RecentProductAdapter(this, null, getLang());
        binding.recViewMostSaleProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recViewMostSaleProducts.setAdapter(productAdapter);
        binding.flCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Preferences.getInstance().getCart(ProductDetailsActivity.this) != null && Preferences.getInstance().getCart(ProductDetailsActivity.this).getProvider_id().equals(product.getProvider_id())) {
                    showDialog();
                } else {
                    addTocart();
                }
            }
        });
        binding.flMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, MarketDetialsActivity.class);
                intent.putExtra("data", product.getProvider());
                startActivity(intent);
            }
        });
        if (getUserModel().getData().getStore_name() != null) {
            binding.flCart.setVisibility(View.GONE);
            binding.flMostSale.setVisibility(View.GONE);
            binding.flMarket.setVisibility(View.GONE);
        } else {
            binding.llmarket.setVisibility(View.GONE);
        }
        binding.flRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvvm.deleteProduct(product.getId(), ProductDetailsActivity.this);
            }
        });
        binding.flEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, AddNewProductActivity.class);
                intent.putExtra("data", product);
                launcher.launch(intent);
            }
        });
    }

    private void addTocart() {


        int size = mvvm.add_to_cart(product, amount, this);
        generalMvvm.getCart().postValue(size);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
        builder.setMessage("\n" + "افراغ السله");
        TextView title = new TextView(ProductDetailsActivity.this);
        title.setText("متجر اخر هل تريد افراغ السله");
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
                                Preferences.getInstance().create_update_cart(ProductDetailsActivity.this, null);
                                addTocart();
                                //finishAffinity();
                            }
                        }).setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                                //finishAffinity();
                            }
                        })
        ;
        builder.show();
    }

    public void showProductDetails(ProductModel productModel) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("id", productModel.getId());
        startActivity(intent);
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


}