package com.app.filtar.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.filtar.model.StatusResponse;
import com.app.filtar.model.UserModel;
import com.app.filtar.remote.Api;
import com.app.filtar.tags.Tags;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityHomeMvvm extends AndroidViewModel {

    public MutableLiveData<String> firebase = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityHomeMvvm(@NonNull Application application) {
        super(application);
    }

    public void updateFirebase(Context context, UserModel userModel) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();

                Api.getService(Tags.base_url)
                        .updateFireBaseToken( userModel.getData().getId(), token)
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
                                    Log.e("errToken",response.body().getMessage().toString()+"__");
                                    if (response.body() != null) {

                                        if (response.body().getStatus() == 200) {

                                           // userModel.getData().setFirebase_token(token);
                                            firebase.postValue(token);
                                            Log.e("token", "updated");

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
        });


    }

}
