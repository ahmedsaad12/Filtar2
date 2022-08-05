package com.app.filtar.tags.services;


import com.app.filtar.model.AddFlterModel;
import com.app.filtar.model.AllAppoinmentModel;
import com.app.filtar.model.BlogDataModel;
import com.app.filtar.model.BlogTagsDataModel;
import com.app.filtar.model.CartModel;
import com.app.filtar.model.CategoryDataModel;
import com.app.filtar.model.CityDataModel;
import com.app.filtar.model.CountryCodeDataModel;
import com.app.filtar.model.GovernateDataModel;
import com.app.filtar.model.NotificationDataModel;
import com.app.filtar.model.OrderDataModel;
import com.app.filtar.model.ProductDataModel;
import com.app.filtar.model.SettingsModel;
import com.app.filtar.model.SingleBlogDataModel;
import com.app.filtar.model.SingleBlogModel;
import com.app.filtar.model.SingleOrderDataModel;
import com.app.filtar.model.SingleProductModel;
import com.app.filtar.model.SliderDataModel;
import com.app.filtar.model.StatusResponse;
import com.app.filtar.model.UserModel;

import java.util.List;

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
            @Part("country_id") RequestBody country_id,
            @Part("governorate_id") RequestBody governorate_id,
            @Part("city_id") RequestBody city_id,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("api/updateUserProfile")
    Single<Response<UserModel>> updateProfile(@Part("user_id") RequestBody user_id,
                                              @Part("first_name") RequestBody first_name,
                                              @Part("last_name") RequestBody last_name,
                                              @Part("address") RequestBody address,
                                              @Part("governorate_id") RequestBody governorate_id,
                                              @Part("city_id") RequestBody city_id,
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
            @Part("country_id") RequestBody country_id,
            @Part("governorate_id") RequestBody governorate_id,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part nationality_id_image,
            @Part MultipartBody.Part vat_number_image,
            @Part MultipartBody.Part commercial_number_image
    );

    @Multipart
    @POST("api/updateProviderProfile")
    Single<Response<UserModel>> updateMarket(
            @Part("provider_id") RequestBody provider_id,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part("address") RequestBody address,
            @Part("governorate_id") RequestBody governorate_id,
            @Part("store_name") RequestBody store_name,

            @Part MultipartBody.Part image

    );

    @POST("api/addFilter")
    Single<Response<StatusResponse>> addFilter(@Body AddFlterModel addFlterModel);

    @FormUrlEncoded
    @POST("api/insertToken")
    Single<Response<StatusResponse>> updateFireBaseToken(@Field("user_id") String user_id,
                                                         @Field("phone_token") String phone_token


    );

    @GET("api/firstCleaningTime")
    Single<Response<AllAppoinmentModel>> getfirstCleaningTime(@Query("user_id") String user_id


    );

    @GET("api/myCleaningTimes")
    Single<Response<AllAppoinmentModel>> getMyCleaningTime(@Query("user_id") String user_id


    );

    @GET("api/sliders")
    Single<Response<SliderDataModel>> getSlider();

    @GET("api/searchExplanations")
    Single<Response<BlogDataModel>> getBlogs(@Query("tag_title") String tag_title);

    @GET("api/latestExplanation")
    Single<Response<SingleBlogDataModel>> getSingleBlogs();

    @GET("api/oneExplanation")
    Single<Response<SingleBlogModel>> getBlogDetails(@Query("explanation_id") String explanation_id);

    @GET("api/allCategories")
    Single<Response<CategoryDataModel>> getCategory();

    @GET("api/allExplanationsTags")
    Single<Response<BlogTagsDataModel>> getTags();

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

    @FormUrlEncoded
    @POST("api/contactUs")
    Single<Response<StatusResponse>> contactUs(@Field("message") String message,
                                               @Field("user_id") String user_id,
                                               @Field("provider_id") String provider_id

    );

    @GET("api/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Query("user_id") String user_id,
                                                             @Query("provider_id") String provider_id
    );

    @POST("api/makeOrder")
    Single<Response<StatusResponse>> sendOrder(
            @Body CartModel cartDataModel
    );

    @GET("api/myOrders")
    Single<Response<OrderDataModel>> getOrders(@Query("user_id") String user_id,
                                               @Query("provider_id") String provider_id);

    @GET("api/countries")
    Single<Response<CountryCodeDataModel>> getCountry();

    @GET("api/governorates")
    Single<Response<GovernateDataModel>> getGovernate(@Query("country_id") String country_id);

    @GET("api/citiesOfGovernorate")
    Single<Response<CityDataModel>> getCity(@Query("governorate_id") String governorate_id);

    @Multipart
    @POST("api/add_product")
    Single<Response<StatusResponse>> addProduct(@Part("title") RequestBody title,
                                                @Part("provider_id") RequestBody provider_id,
                                                @Part List<MultipartBody.Part> images,
                                                @Part("price") RequestBody price,
                                                @Part("category_id") RequestBody category_id,
                                                @Part("details") RequestBody details,
                                                @Part MultipartBody.Part image


    );

    @Multipart
    @POST("api/editProduct")
    Single<Response<StatusResponse>> updateProduct(@Part("title") RequestBody title,
                                                   @Part("provider_id") RequestBody provider_id,
                                                   @Part List<MultipartBody.Part> images,
                                                   @Part("price") RequestBody price,
                                                   @Part("category_id") RequestBody category_id,
                                                   @Part("details") RequestBody details,
                                                   @Part("product_id") RequestBody product_id


    );

    @FormUrlEncoded
    @POST("api/logout")
    Single<Response<StatusResponse>> logout(@Field("user_id") String user_id,
                                            @Field("provider_id") String provider_id,
                                            @Field("phone_token") String phone_token
    );


    @FormUrlEncoded
    @POST("api/editFilter")
    Single<Response<StatusResponse>> editfilter(@Field("user_id") String user_id,
                                                @Field("candle_number") String candle_number,
                                                @Field("last_clean_time") String last_clean_time
    );

    @GET("api/setting")
    Single<Response<SettingsModel>> getSettings();

    @GET("api/orderDetails")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id);

    @FormUrlEncoded
    @POST("api/updateOrderStatus")
    Single<Response<StatusResponse>> acceptRefues(@Field("order_id") String order_id,
                                                  @Field("provider_id") String provider_id,
                                                  @Field("status") String status);

    @FormUrlEncoded
    @POST("api/deleteProduct")
    Single<Response<StatusResponse>> deleteProduct(@Field("product_id") String product_id
    );
}