package com.xpeho.spring_boot_java_random_user.data.sources;

import com.xpeho.spring_boot_java_random_user.data.models.RandomUserResponseDAO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {
    @GET("/api/")
    Call<RandomUserResponseDAO> getRandomUsers(@Query("results") int results);
}
