package com.app.filtar.uis.activity_home.home_module;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.filtar.R;
import com.app.filtar.adapter.RecentProductAdapter;
import com.app.filtar.adapter.SliderAdapter;
import com.app.filtar.databinding.FragmentHomeBinding;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.BlogModel;
import com.app.filtar.model.ProductModel;
import com.app.filtar.model.SliderDataModel;
import com.app.filtar.mvvm.FragmentHomeMvvm;
import com.app.filtar.mvvm.GeneralMvvm;
import com.app.filtar.uis.activity_add_filter.AddFilterActivity;
import com.app.filtar.uis.activity_base.BaseFragment;
import com.app.filtar.uis.activity_blogs_details.BlogDetailsActivity;
import com.app.filtar.uis.activity_home.HomeActivity;
import com.app.filtar.uis.activity_product_details.ProductDetailsActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private GeneralMvvm generalMvvm;
    private FragmentHomeMvvm fragmentHomeMvvm;
    private SliderAdapter sliderAdapter;
    private List<SliderDataModel.SliderModel> sliderModelList;
    private Timer timer;
    private RecentProductAdapter recentProductAdapter;
    private boolean isInFullScreen = false;
    private SimpleDateFormat dateFormat;

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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        sliderModelList=new ArrayList<>();
        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        fragmentHomeMvvm.getOnDataSuccess().observe(activity, new Observer<BlogModel>() {
            @Override
            public void onChanged(BlogModel blogModel) {
                binding.setModel1(blogModel);
                setUpYoutube( blogModel.getLink());
            }
        });
        fragmentHomeMvvm.getIsLoading().observe(activity, isLoading -> {
            if (isLoading) {
                binding.loaderSlider.setVisibility(View.VISIBLE);

            }
            // binding.swipeRefresh.setRefreshing(isLoading);
        });
        fragmentHomeMvvm.getIsLoadingRecentProduct().observe(activity, isLoading -> {
            if (!isLoading) {
                binding.loaderMostSales.stopShimmer();
                binding.loaderMostSales.setVisibility(View.GONE);
            }
        });
        fragmentHomeMvvm.getSliderDataModelMutableLiveData().observe(activity, new androidx.lifecycle.Observer<SliderDataModel>() {
            @Override
            public void onChanged(SliderDataModel sliderDataModel) {

                if (sliderDataModel.getData() != null) {
                    binding.loaderSlider.setVisibility(View.GONE);
                    sliderModelList.clear();
                    sliderModelList.addAll(sliderDataModel.getData());
                    sliderAdapter.notifyDataSetChanged();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTask(), 3000, 3000);
                }

            }
        });
        fragmentHomeMvvm.getOnRecentProductDataModel().observe(activity, list -> {
            binding.swipeRefresh.setRefreshing(false);
            if (list.size() > 0) {
                binding.tvNoMostSaleProduct.setVisibility(View.GONE);
            } else {
                binding.tvNoMostSaleProduct.setVisibility(View.VISIBLE);
            }
            if (recentProductAdapter != null) {
                recentProductAdapter.updateList(list);

            }

        });

        sliderAdapter = new SliderAdapter(sliderModelList, activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPadding(80, 0, 80, 0);
        binding.pager.setPageMargin(20);
        fragmentHomeMvvm.getSlider();
        recentProductAdapter = new RecentProductAdapter(activity, this, getLang());
        binding.recViewMostSaleProducts.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewMostSaleProducts.setAdapter(recentProductAdapter);
        fragmentHomeMvvm.getRecentProduct();

        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
generalMvvm.getAllAppoinmentModelMutableLiveData().observe(activity, new Observer<AllAppoinmentModel>() {
    @Override
    public void onChanged(AllAppoinmentModel allAppoinmentModel) {
        if(allAppoinmentModel!=null){
            try {
              Date  dateEnd=  dateFormat.parse(allAppoinmentModel.getData().get(0).getComing_clean_time());
                getDifferent(new Date(System.currentTimeMillis()),dateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            binding.setModel(allAppoinmentModel.getData().get(0));
        }
    }
});
generalMvvm.getFilter().observe(activity, new Observer<Integer>() {
    @Override
    public void onChanged(Integer integer) {
        if(integer==1){
            if(getUserModel()!=null){
                generalMvvm.getfirstCleaningTime(getUserModel());}
        }
    }
});
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
        binding.llSeeAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    generalMvvm.getOrderpage().setValue(1);

            }
        });
        binding.llSeeAllExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalMvvm.getOrderpage().setValue(2);

            }
        });
        if(getUserModel()!=null){
        generalMvvm.getfirstCleaningTime(getUserModel());}
        fragmentHomeMvvm.getLastBLog();

    }

    public void showProductDetails(ProductModel productModel) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("id", productModel.getId());
        startActivity(intent);
    }

    public void navigateToDetails(String id) {
        Intent intent=new Intent(activity, BlogDetailsActivity.class);
        intent.putExtra("data",id);
        startActivity(intent);
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }

    }
    private void setUpYoutube( String url) {
        String videoId = extractYTId(url);
        if (videoId != null) {

            binding.youtubePlayerView.setEnableAutomaticInitialization(false);
            binding.youtubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(videoId, 0);

                }
            }, true);


        } else {
        }


    }

    private String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile("^https?://.*(?:www\\.youtube\\.com/|v/|u/\\w/|embed/|watch\\?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }

    public  void getDifferent(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();




        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthInMili = daysInMilli * 30;

        long elapsedMonths = different / monthInMili;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
binding.setMonth(elapsedMonths+"");
        binding.setHour(elapsedHours+"");
        binding.setDay(elapsedDays+"");




    }
}