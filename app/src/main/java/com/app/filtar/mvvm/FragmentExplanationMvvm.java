package com.app.filtar.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.filtar.model.BlogDataModel;
import com.app.filtar.model.BlogModel;
import com.app.filtar.model.BlogTagsDataModel;
import com.app.filtar.model.BlogTagsModel;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.remote.Api;
import com.app.filtar.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentExplanationMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<List<BlogModel>> onDataSuccess;
    private MutableLiveData<List<BlogTagsModel>> onCategoryDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public FragmentExplanationMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }
    public MutableLiveData<List<BlogTagsModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }

    public MutableLiveData<List<BlogModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public void getBlogs(String title) {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getBlogs(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<BlogDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<BlogDataModel> response) {
                        getIsDataLoading().postValue(false);

                        if (response.isSuccessful() && response.body() !=null) {
                            if (response.body().getStatus()==200){
                                onDataSuccess.setValue(response.body().getData());
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsDataLoading().setValue(false);
                    }
                });
    }
    public void getCategory() {
        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<BlogTagsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<BlogTagsDataModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsDataLoading().setValue(false);

                                getOnCategoryDataSuccess().setValue(response.body().getData());

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

}
