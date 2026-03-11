package com.xpeho.spring_boot_java_random_user.data.models.api.dummy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DummyUserResponse {
    @SerializedName("users")
    private List<DummyUserResultDTO> users;

    @SerializedName("total")
    private int total;

    @SerializedName("skip")
    private int skip;

    @SerializedName("limit")
    private int limit;

    public List<DummyUserResultDTO> getUsers() {
        return users;
    }

    public void setUsers(List<DummyUserResultDTO> users) {
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

