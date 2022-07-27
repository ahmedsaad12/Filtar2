package com.app.filtar.uis.activity_blogs_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.app.filtar.R;
import com.app.filtar.databinding.ActivityBlogDetailsBinding;
import com.app.filtar.model.BlogModel;
import com.app.filtar.mvvm.ActivityBlogDetailsMvvm;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogDetailsActivity extends BaseActivity {
    private ActivityBlogDetailsBinding binding;
    private ActivityBlogDetailsMvvm mvvm;
    private String id;
    private boolean isInFullScreen = false;
    private BlogModel blogModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_blog_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        id =  intent.getStringExtra("data");

    }
    private void initView() {

        setUpToolbar(binding.toolbar, getString(R.string.blog_details), R.color.white, R.color.black);
        binding.setLang(getLang());


        mvvm = ViewModelProviders.of(this).get(ActivityBlogDetailsMvvm.class);

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);
            }
        });

        mvvm.getOnDataSuccess().observe(this, blogModel -> {
            if (blogModel!=null){
                binding.setModel(blogModel);
                this.blogModel=blogModel;
                if (blogModel.getLink() != null) {
                    binding.image.setVisibility(View.GONE);
                    setUpYoutube( blogModel.getLink());



                }else{
                    binding.flvideo.setVisibility(View.GONE);
                }
            }
        });

        mvvm.getSingleBlog(id,getLang());
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

}