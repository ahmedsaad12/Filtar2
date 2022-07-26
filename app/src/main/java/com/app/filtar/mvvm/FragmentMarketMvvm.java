package com.app.filtar.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.filtar.model.CategoryDataModel;
import com.app.filtar.model.CategoryModel;
import com.app.filtar.model.ProductDataModel;
import com.app.filtar.model.ProductModel;
import com.app.filtar.model.UserModel;
import com.app.filtar.remote.Api;
import com.app.filtar.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentMarketMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<List<CategoryModel>> onCategoryDataSuccess;


    private MutableLiveData<List<ProductModel>> onProductsDataSuccess;

    private MutableLiveData<String> categoryId;


    private MutableLiveData<String> query;

    private MutableLiveData<Integer> categoryPos;




    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentMarketMvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }





    public MutableLiveData<List<ProductModel>> getOnProductsDataSuccess() {
        if (onProductsDataSuccess == null) {
            onProductsDataSuccess = new MutableLiveData<>();
        }
        return onProductsDataSuccess;
    }



    public MutableLiveData<Integer> getCategoryPos() {
        if (categoryPos == null) {
            categoryPos = new MutableLiveData<>();
        }
        return categoryPos;
    }

    public MutableLiveData<String> getCategoryId() {
        if (categoryId == null) {
            categoryId = new MutableLiveData<>();
        }
        return categoryId;
    }

    public MutableLiveData<String> getQuery() {
        if (query == null) {
            query = new MutableLiveData<>();
        }
        return query;
    }



    public void setCategoryId(String categoryId, Context context, UserModel userModel) {
        getCategoryId().setValue(categoryId);
    }

    public void getCategory() {
        getIsLoading().setValue(true);
        Api.getService(Tags.base_url).getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsLoading().setValue(false);

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


    public void searchProduct(String query,String minPrice,String maxPrice,String provider_id) {

        getIsLoading().postValue(true);

        Api.getService(Tags.base_url).searchByCatProduct(getCategoryId().getValue(), provider_id,minPrice,maxPrice,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ProductDataModel> response) {
                        getIsLoading().setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
getOnProductsDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsLoading().setValue(false);
                        Log.e("error", e.toString());
                    }
                });
    }



}
