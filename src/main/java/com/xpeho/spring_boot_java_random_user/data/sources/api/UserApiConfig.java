package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.sources.api.dummy.DummyUserApi;
import com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser.RandomUserApi;
import jakarta.annotation.PreDestroy;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class UserApiConfig {

    private OkHttpClient okHttpClient;

    /**
     * Single shared OkHttpClient intentionally reused by both Retrofit instances (dummyUserRetrofit and randomUserRetrofit).
     * Sharing a single client allows both APIs to benefit from a common connection pool, thread pool,
     * and keep-alive settings, reducing resource consumption.
     * If the two APIs ever require distinct timeouts or interceptors, separate clients should be created.
     * The {@link jakarta.annotation.PreDestroy} hook ensures the client is shut down cleanly on application stop.
     */
    @Bean
    public OkHttpClient okHttpClient() {
        okHttpClient = new OkHttpClient.Builder().build();
        return okHttpClient;
    }

    @Bean(name = "dummyUserRetrofit")
    public Retrofit dummyUserRetrofit(OkHttpClient okHttpClient, Environment env) {
        return new Retrofit.Builder()
                .baseUrl(env.getRequiredProperty("dummy.api.base-url"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public DummyUserApi dummyUserApi(@Qualifier("dummyUserRetrofit") Retrofit dummyUserRetrofit) {
        return dummyUserRetrofit.create(DummyUserApi.class);
    }

    @Bean(name = "randomUserRetrofit")
    public Retrofit randomUserRetrofit(OkHttpClient okHttpClient, Environment env) {
        return new Retrofit.Builder()
                .baseUrl(env.getRequiredProperty("randomuser.api.base-url"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public RandomUserApi randomUserApi(@Qualifier("randomUserRetrofit") Retrofit randomUserRetrofit) {
        return randomUserRetrofit.create(RandomUserApi.class);
    }

    @PreDestroy
    public void destroy() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().executorService().shutdown();
            okHttpClient.connectionPool().evictAll();
        }
    }
}
