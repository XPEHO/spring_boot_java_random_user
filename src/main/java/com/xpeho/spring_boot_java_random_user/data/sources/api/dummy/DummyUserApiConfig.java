package com.xpeho.spring_boot_java_random_user.data.sources.api.dummy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class DummyUserApiConfig {
    @Bean(name = "dummyUserRetrofit")
    public Retrofit dummyUserRetrofit(Environment env) {
        return new Retrofit.Builder()
                .baseUrl(env.getRequiredProperty("dummy.api.base-url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public DummyUserApi dummyUserApi(@Qualifier("dummyUserRetrofit") Retrofit dummyUserRetrofit) {
        return dummyUserRetrofit.create(DummyUserApi.class);
    }
}

