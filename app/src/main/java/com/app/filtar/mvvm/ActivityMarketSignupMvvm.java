package com.app.filtar.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.filtar.R;
import com.app.filtar.model.SignUpMarketModel;
import com.app.filtar.model.SignUpModel;
import com.app.filtar.model.UserModel;
import com.app.filtar.remote.Api;
import com.app.filtar.share.Common;
import com.app.filtar.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityMarketSignupMvvm extends AndroidViewModel {
    private final String TAG = ActivityMarketSignupMvvm.class.getName();

    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityMarketSignupMvvm(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public void signUp(SignUpMarketModel model, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody first_name_part = Common.getRequestBodyText(model.getFirst_name());
        RequestBody last_name_part = Common.getRequestBodyText(model.getLast_name());

        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());
        RequestBody address_part = Common.getRequestBodyText(model.getAddress());
        RequestBody store_part = Common.getRequestBodyText(model.getStore_name());
        RequestBody com_part = Common.getRequestBodyText(model.getCommercial_num());
        RequestBody tax_part = Common.getRequestBodyText(model.getTax_num());
        MultipartBody.Part imageId = Common.getMultiPart(context, Uri.parse(model.getImage_id_uri()), "nationality_id_image");
        MultipartBody.Part imageCommercial = Common.getMultiPart(context, Uri.parse(model.getImage_commercial_uri()), "commercial_number_image");
        MultipartBody.Part imageTax = Common.getMultiPart(context, Uri.parse(model.getImage_tax_uri()), "vat_number_image");


        MultipartBody.Part image = null;
        if (model.getImage_uri() != null && !model.getImage_uri().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(model.getImage_uri()), "logo");
        }


        Api.getService(Tags.base_url).signUpMarket( first_name_part, last_name_part, address_part, phone_code_part, phone_part,store_part,tax_part,com_part, image,imageId,imageTax,imageCommercial)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Log.e("", e.getMessage());

                    }
                });
    }

    public void update(SignUpModel model, String token,String country, Context context) {
//        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        RequestBody country_part = Common.getRequestBodyText(country);
//
//        RequestBody first_name_part = Common.getRequestBodyText(model.getFirst_name());
//        RequestBody last_name_part = Common.getRequestBodyText(model.getLast_name());
//
//        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
//        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());
//        RequestBody email_part = Common.getRequestBodyText(model.getEmail());
//
//
//        MultipartBody.Part image = null;
//        if (model.getImage_uri() != null && !model.getImage_uri().isEmpty()) {
//            image = Common.getMultiPart(context, Uri.parse(model.getImage_uri()), "image");
//        }
//
//
//        Api.getService(Tags.base_url).updateProfile(token,country_part, first_name_part, last_name_part, email_part, phone_code_part, phone_part, image)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response<UserModel>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable.add(d);
//                    }
//
//                    @Override
//                    public void onSuccess(Response<UserModel> response) {
//                        dialog.dismiss();
//                        if (response.isSuccessful()) {
//
//                            if (response.body() != null) {
//                                if (response.body().getCode() == 200) {
//
//                                    getUserData().setValue(response.body());
//                                } else if (response.body().getCode() == 409) {
//                                    Toast.makeText(context, R.string.ph_found, Toast.LENGTH_LONG).show();
//                                } else if (response.body().getCode() == 410) {
//                                    Toast.makeText(context, R.string.em_found, Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        dialog.dismiss();
//                        Log.e("", e.getMessage());
//
//                    }
//                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
