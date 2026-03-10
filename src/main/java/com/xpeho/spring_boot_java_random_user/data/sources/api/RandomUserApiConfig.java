package com.xpeho.spring_boot_java_random_user.data.sources.api;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RandomUserApiConfig {
    @Bean
    public Retrofit randomUserRetrofit(Environment env) {
        String baseUrl = env.getRequiredProperty("dummy.api.base-url");
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Bean
    public RandomUserApi randomUserApi(Retrofit randomUserRetrofit) {
        return randomUserRetrofit.create(RandomUserApi.class);
    }
}
