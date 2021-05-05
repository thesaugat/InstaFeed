package com.thesaugat.instafeed.api;

import com.thesaugat.instafeed.pojo.FeedData;
import com.thesaugat.instafeed.pojo.LoginResponse;
import com.thesaugat.instafeed.pojo.ServerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiServices {

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST
    Call<ServerResponse> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("/api/v1/post-feed")
    Call<ServerResponse> postNewFeed(@Header("Apikey") String apiKey, @Field("post-desc") String postDesc);


    @POST("/api/v1/get-all-feed")
    Call<FeedData> getNewsFeed(@Header("Apikey") String apiKey);

    @POST("/api/v1/get-my-feed")
    Call<FeedData> getMyFeed(@Header("Apikey") String apiKey);

    @POST("/api/v1/post-feed")
    @Multipart
    Call<ServerResponse> uploadAPost(
            @Header("Apikey") String token,
            @Part("post_desc") RequestBody desc,
            @Part MultipartBody.Part file
    );


    @FormUrlEncoded
    @POST("/api/v1/follow")
    Call<ServerResponse> follow(@Header("Apikey") String apiKey, @Field("following_id") int following_id);

    @FormUrlEncoded
    @POST("/api/v1/unfollow")
    Call<ResponseBody> unFollow(@Header("Apikey") String apiKey, @Field("following_id") int following_id);
}
