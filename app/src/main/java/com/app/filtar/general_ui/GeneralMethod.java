package com.app.filtar.general_ui;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.app.filtar.model.BlogModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.app.filtar.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                if (view instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) view;
                    if (imageUrl != null) {
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                } else if (view instanceof RoundedImageView) {
                    RoundedImageView imageView = (RoundedImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);

                    }
                } else if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                }

            }
        });


    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {


        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);
            }
        }

    }

    @BindingAdapter({"title", "from", "to"})
    public static void displayDate(TextView textView, String title, long from, long to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        if (title != null) {
            textView.setText(title);
        } else {
            textView.setText(dateFormat.format(new Date(from * 1000)) + " - " + dateFormat.format(new Date(to * 1000)));
        }

    }

    @BindingAdapter("createDate")
    public static void createAtDate(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                String d = format.format(parse);
                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @BindingAdapter("createTime")
    public static void createAtTime(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                String time = format.format(parse);
                textView.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @BindingAdapter("statusTime")
    public static void orderStatusTime(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String time = "";
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd\nhh:mm a", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                time = format.format(parse);
                textView.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }





    @BindingAdapter("getTags")
    public static void getTags(TextView textView, BlogModel blogModel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
       String tags="";
       if(blogModel!=null){
       for(int i=0;i<blogModel.getTags().size();i++){
           tags+="#"+blogModel.getTags().get(i).getTitle();
       }
       textView.setText(tags);
    }}



    @BindingAdapter("status")
    public static void orderStatus(TextView textView, String status) {
        if (status != null) {
            if (status.equals("new")) {
                textView.setText("جارى الموافقه على طلبك ");
            } else if (status.equals("refused_by_provider")) {
                textView.setText("تم رفض طلبك");
            } else if (status.equals("accepted_by_provider")) {
                textView.setText("تم قبول طلبك");

            }
        }

    }


}













