package com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RandomUserApiConfig {
    @Bean(name = "randomUserRetrofit")
    public Retrofit randomUserRetrofit(Environment env) {
        return new Retrofit.Builder()
                .baseUrl(env.getRequiredProperty("randomuser.api.base-url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public RandomUserApi randomUserApi(@Qualifier("randomUserRetrofit") Retrofit randomUserRetrofit) {
        return randomUserRetrofit.create(RandomUserApi.class);
    }
}

