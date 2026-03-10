package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomUserResponse {
    @SerializedName("users")
    private List<RandomUserResultDAO> users;

    @SerializedName("total")
    private int total;

    @SerializedName("skip")
    private int skip;

    @SerializedName("limit")
    private int limit;

    public List<RandomUserResultDAO> getUsers() {
        return users;
    }

    public void setUsers(List<RandomUserResultDAO> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
