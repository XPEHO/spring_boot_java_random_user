package com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser;

import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {
    @GET(".")
    Call<RandomUserResponseDTO> getUsers(@Query("results") int results, @Query("page") int page);
}

