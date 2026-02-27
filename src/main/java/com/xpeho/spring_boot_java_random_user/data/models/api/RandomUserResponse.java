package com.xpeho.spring_boot_java_random_user.data.models.api;

public class RandomUserResponse {
    private RandomUserResultDAO[] results;

    public RandomUserResultDAO[] getResults() {
        return results;
    }

    public void setResults(RandomUserResultDAO[] results) {
        this.results = results;
    }
}
