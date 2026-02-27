package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {
    @GET("/api/")
    Call<RandomUserResponse> getRandomUsers(@Query("results") int results);
}
