package com.filtar.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.filtar.R;
import com.filtar.model.AddFlterModel;
import com.filtar.model.StatusResponse;
import com.filtar.remote.Api;
import com.filtar.share.Common;
import com.filtar.tags.Tags;
import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityAddFilterMvvm extends AndroidViewModel {


    public MutableLiveData<Boolean> save = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityAddFilterMvvm(@NonNull Application application) {
        super(application);
    }





    public void addFilter(Context context, AddFlterModel addFlterModel){
        Gson gson = new Gson();
        String user_data = gson.toJson(addFlterModel);
        Log.e("ddkdk",user_data);
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url).addFilter(addFlterModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        Log.e("dlldl",response.code()+" "+response.body().getStatus());
                        try {
                            Log.e("Elllfl",response.code()+""+response.errorBody().string());
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                save.postValue(true);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error",e.toString());
                    }
                });
    }

}
