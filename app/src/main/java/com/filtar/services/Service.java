package com.filtar.services;



import com.filtar.model.AddFlterModel;
import com.filtar.model.StatusResponse;
import com.filtar.model.UserModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {



    @FormUrlEncoded
    @POST("api/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone
    );

    @Multipart
    @POST("api/user_register")
    Single<Response<UserModel>> signUp(
                                       @Part("first_name") RequestBody first_name,
                                       @Part("last_name") RequestBody last_name,
                                       @Part("address") RequestBody address,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("phone") RequestBody phone,
                                       @Part MultipartBody.Part image
    );


    @Multipart
    @POST("api/provider_register")
    Single<Response<UserModel>> signUpMarket(
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("address") RequestBody address,
            @Part("phone_code") RequestBody phone_code,
            @Part("phone") RequestBody phone,
            @Part("store_name") RequestBody store_name,
            @Part("vat_number") RequestBody vat_number,
            @Part("commercial_number") RequestBody commercial_number,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part nationality_id_image,
            @Part MultipartBody.Part vat_number_image,
            @Part MultipartBody.Part commercial_number_image
    );
    @POST("api/addFilter")
    Single<Response<StatusResponse>> addFilter(@Body AddFlterModel addFlterModel);
    @FormUrlEncoded
    @POST("api/insertToken")
    Single<Response<StatusResponse>> updateFireBaseToken(   @Field("user_id") String user_id,
                                                         @Field("phone_token") String phone_token


    );

}