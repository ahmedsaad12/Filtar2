package com.app.filtar.tags.services;



import com.app.filtar.model.AddFlterModel;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.BlogDataModel;
import com.app.filtar.model.CategoryDataModel;
import com.app.filtar.model.ProductDataModel;
import com.app.filtar.model.SingleBlogModel;
import com.app.filtar.model.SingleProductModel;
import com.app.filtar.model.SliderDataModel;
import com.app.filtar.model.StatusResponse;
import com.app.filtar.model.UserModel;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @GET("api/firstCleaningTime")
    Single<Response<AllAppoinmentModel>> getfirstCleaningTime(@Query("user_id") String user_id


    );
    @GET("api/sliders")
    Single<Response<SliderDataModel>> getSlider();
    @GET("api/home/blogs")
    Single<Response<BlogDataModel>> getBlogs();

    @GET("api/home/blogs")
    Single<Response<SingleBlogModel>> getBlogDetails(@Query("blog_id") String blog_id);
    @GET("api/allCategories")
    Single<Response<CategoryDataModel>> getCategory();


    @GET("api/latestProducts")
    Single<Response<ProductDataModel>> getRecentProduct();

    @GET("api/products")
    Single<Response<ProductDataModel>> searchByCatProduct(@Query("category_id") String category_id,
                                                          @Query("provider_id") String provider_id,
                                                          @Query("min_price") String min_price,
                                                          @Query("max_price") String max_price,
                                                          @Query("search_word") String search_word);
    @GET("api/product_details")
    Single<Response<SingleProductModel>> getSingleProduct(
                                                          @Query("product_id") String product_id);
}