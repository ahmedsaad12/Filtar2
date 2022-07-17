package com.filtar.notifications;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.filtar.R;
import com.filtar.model.StatusResponse;
import com.filtar.model.UserModel;
import com.filtar.preferences.Preferences;
import com.filtar.remote.Api;
import com.filtar.share.App;
import com.filtar.tags.Tags;
import com.filtar.uis.activity_home.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class FireBaseNotifications extends FirebaseMessagingService {
    private CompositeDisposable disposable = new CompositeDisposable();
    private Map<String, String> map;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        map = remoteMessage.getData();

        for (String key : map.keySet()) {
            Log.e("Key=", key + "_value=" + map.get(key));
        }

        manageNotification(map);


    }
    private void manageNotification(Map<String, String> map) {
        String title = map.get("title");
        String body = map.get("message");
        String notification_type = map.get("type");


        String sound_Path = "";
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        sound_Path = uri.toString();
        Intent cancelIntent = new Intent(this, BroadcastCancelNotification.class);
        PendingIntent cancelPending;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

            cancelPending = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            cancelPending = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setAutoCancel(true)
                .setOngoing(false)
                .setChannelId(App.CHANNEL_ID)
                .setDeleteIntent(cancelPending)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(Uri.parse(sound_Path), AudioManager.STREAM_NOTIFICATION);


        if (notification_type.equals("100")||notification_type.equals("200")||notification_type.equals("300")) {
            notificationCompat.setContentTitle(title);
            notificationCompat.setContentText(body);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("notificationTitle", title);
            intent.putExtra("NotificationMsg", body);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

                notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_MUTABLE));
            } else {
                notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            notificationCompat.setLargeIcon(bitmap);
            manager.notify(Tags.not_id, notificationCompat.build());
           // EventBus.getDefault().post(new NotiFire(order_status));

        }


    }



    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (getUserModel() == null) {
            return;
        }

        Api.getService(Tags.base_url)
                .updateFireBaseToken(getUserModel().getData().getId(), s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatus() == 200) {
                                    UserModel userModel = getUserModel();
                                    userModel.setFirebase_token(s);
                                    setUserModel(userModel);

                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("token", e.toString());

                    }
                });

    }

    public UserModel getUserModel() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserData(this);
    }


    public void setUserModel(UserModel userModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.createUpdateUserData(this, userModel);

    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
