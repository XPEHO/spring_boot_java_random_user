package com.xpeho.spring_boot_java_random_user.data.models.api.dummy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DummyUserResponse {
    @SerializedName("users")
    private List<DummyUserResultDTO> users;

    public List<DummyUserResultDTO> getUsers() {
        return users;
    }

    public void setUsers(List<DummyUserResultDTO> users) {
        this.users = users;
    }
}
