package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RandomUserResponse {
    @SerializedName("users")
    private List<RandomUserResultDAO> users;

    public List<RandomUserResultDAO> getUsers() {
        return users;
    }

    public void setUsers(List<RandomUserResultDAO> users) {
        this.users = users;
    }
}
