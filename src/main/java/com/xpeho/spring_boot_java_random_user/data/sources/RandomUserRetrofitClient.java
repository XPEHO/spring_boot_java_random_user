package com.xpeho.spring_boot_java_random_user.data.sources;

import okhttp3.OkHttpClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RandomUserRetrofitClient {
    private final RandomUserApi apiInstance;

    public RandomUserRetrofitClient(Environment env) {
        String baseUrl = env.getProperty("randomuser.api.base-url", "https://randomuser.me/");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiInstance = retrofit.create(RandomUserApi.class);
    }

    public RandomUserApi getApi() {
        return apiInstance;
    }
}
