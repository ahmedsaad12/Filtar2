package com.app.filtar.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.filtar.R;
import com.app.filtar.model.AddFlterModel;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.StatusResponse;
import com.app.filtar.model.UserModel;
import com.app.filtar.remote.Api;
import com.app.filtar.share.Common;
import com.app.filtar.tags.Tags;
import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GeneralMvvm extends AndroidViewModel {
    private MutableLiveData<Integer> orderpage;
    private MutableLiveData<Boolean> onCurrentOrderRefreshed;
    private MutableLiveData<Boolean> onPreOrderRefreshed;
    private MutableLiveData<Boolean> onStaticRefreshed;
    private MutableLiveData<Boolean> onUserLoggedIn;
    private MutableLiveData<AllAppoinmentModel> allAppoinmentModelMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public GeneralMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getOrderpage() {
        if (orderpage == null) {
            orderpage = new MutableLiveData<>();
        }
        return orderpage;
    }
    public MutableLiveData<Boolean> getOnCurrentOrderRefreshed() {
        if (onCurrentOrderRefreshed == null) {
            onCurrentOrderRefreshed = new MutableLiveData<>();
        }
        return onCurrentOrderRefreshed;
    }
    public MutableLiveData<Boolean> getOnPreOrderRefreshed() {
        if (onPreOrderRefreshed == null) {
            onPreOrderRefreshed = new MutableLiveData<>();
        }
        return onPreOrderRefreshed;
    }

    public MutableLiveData<Boolean> getOnStaticRefreshed() {
        if (onStaticRefreshed == null) {
            onStaticRefreshed = new MutableLiveData<>();
        }
        return onStaticRefreshed;    }
    public MutableLiveData<Boolean> getOnUserLoggedIn() {
        if (onUserLoggedIn == null) {
            onUserLoggedIn = new MutableLiveData<>();
        }
        return onUserLoggedIn;
    }

    public MutableLiveData<AllAppoinmentModel> getAllAppoinmentModelMutableLiveData() {
        if (allAppoinmentModelMutableLiveData == null) {
            allAppoinmentModelMutableLiveData = new MutableLiveData<>();
        }
        return allAppoinmentModelMutableLiveData;
    }

    public void getfirstCleaningTime(UserModel userModel){


        Api.getService(Tags.base_url).getfirstCleaningTime(userModel.getData().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AllAppoinmentModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AllAppoinmentModel> response) {

                        Log.e("dlldl",response.code()+" "+response.body().getStatus());
                        try {
                            Log.e("Elllfl",response.code()+""+response.errorBody().string());
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                allAppoinmentModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e("error",e.toString());
                    }
                });
    }

}
