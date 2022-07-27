package com.app.filtar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.app.filtar.R;
import com.app.filtar.databinding.BlogRowBinding;
import com.app.filtar.model.BlogModel;
import com.app.filtar.uis.activity_home.explanation_module.FragmentExplanation;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<BlogModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;
    private Fragment fragment;

    public BlogAdapter(List<BlogModel> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment=fragment;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BlogRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.blog_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        if (list.get(position).getLink() != null) {
            setUpYoutube(((MyHolder) holder).binding, list.get(position).getLink());



        }else{
            ((MyHolder) holder).binding.flvideo.setVisibility(View.GONE);
        }

        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentExplanation){
                FragmentExplanation fragmentBlogs=(FragmentExplanation) fragment;
                fragmentBlogs.navigateToDetails(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        BlogRowBinding binding;

        public MyHolder(BlogRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void updateList(List<BlogModel> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
    private void setUpYoutube(BlogRowBinding binding, String url) {
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
