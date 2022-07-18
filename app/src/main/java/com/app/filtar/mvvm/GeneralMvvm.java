package com.app.filtar.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GeneralMvvm extends AndroidViewModel {
    private MutableLiveData<Integer> orderpage;
    private MutableLiveData<Boolean> onCurrentOrderRefreshed;
    private MutableLiveData<Boolean> onPreOrderRefreshed;
    private MutableLiveData<Boolean> onStaticRefreshed;
    private MutableLiveData<Boolean> onUserLoggedIn;


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
}
