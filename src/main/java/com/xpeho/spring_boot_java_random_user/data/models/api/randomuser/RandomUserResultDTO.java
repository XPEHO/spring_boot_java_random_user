package com.xpeho.spring_boot_java_random_user.data.models.api.randomuser;

public class RandomUserResultDTO {
    private String gender;
    private RandomUserNameDTO name;
    private String email;
    private String phone;
    private RandomUserPictureDTO picture;
    private String nat;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public RandomUserNameDTO getName() {
        return name;
    }

    public void setName(RandomUserNameDTO name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RandomUserPictureDTO getPicture() {
        return picture;
    }

    public void setPicture(RandomUserPictureDTO picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }
}

