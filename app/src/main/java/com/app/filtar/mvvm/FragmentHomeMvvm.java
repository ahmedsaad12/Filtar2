package com.app.filtar.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.filtar.model.ProductDataModel;
import com.app.filtar.model.ProductModel;
import com.app.filtar.model.SliderDataModel;
import com.app.filtar.remote.Api;
import com.app.filtar.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentHomeMvvm extends AndroidViewModel {
    private static final String TAG = "FragmentHomeMvvm";
    private Context context;
    private MutableLiveData<SliderDataModel> sliderDataModelMutableLiveData;
    private MutableLiveData<Boolean> isLoadingRecentProduct;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoadingLiveData;
    private MutableLiveData<List<ProductModel>> onRecentProductDataModel;

    public FragmentHomeMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<ProductModel>> getOnRecentProductDataModel() {
        if (onRecentProductDataModel == null) {
            onRecentProductDataModel = new MutableLiveData<>();
        }
        return onRecentProductDataModel;
    }
    public MutableLiveData<SliderDataModel> getSliderDataModelMutableLiveData() {
        if (sliderDataModelMutableLiveData == null) {
            sliderDataModelMutableLiveData = new MutableLiveData<>();
        }
        return sliderDataModelMutableLiveData;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLiveData == null) {
            isLoadingLiveData = new MutableLiveData<>();
        }
        return isLoadingLiveData;
    }
    public MutableLiveData<Boolean> getIsLoadingRecentProduct() {
        if (isLoadingRecentProduct == null) {
            isLoadingRecentProduct = new MutableLiveData<>();
        }
        return isLoadingRecentProduct;
    }
    public void getSlider() {
        isLoadingLiveData.setValue(true);
        Api.getService(Tags.base_url).getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SliderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SliderDataModel> response) {
                        isLoadingLiveData.postValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                sliderDataModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoadingLiveData.setValue(false);
                    }
                });
    }

    public void getRecentProduct() {

        getIsLoadingRecentProduct().setValue(true);
        Api.getService(Tags.base_url).getRecentProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ProductDataModel> response) {
                        getIsLoadingRecentProduct().setValue(false);

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                            getOnRecentProductDataModel().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
