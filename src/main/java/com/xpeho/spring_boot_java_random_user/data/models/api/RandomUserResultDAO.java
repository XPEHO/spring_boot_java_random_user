package com.xpeho.spring_boot_java_random_user.data.models.api;

public class RandomUserResultDAO {
    private String gender;
    private RandomUserNameDAO name;
    private String email;
    private String phone;
    private RandomUserPictureDAO picture;
    private String nat;

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public RandomUserNameDAO getName() { return name; }
    public void setName(RandomUserNameDAO name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public RandomUserPictureDAO getPicture() { return picture; }
    public void setPicture(RandomUserPictureDAO picture) { this.picture = picture; }
    public String getNat() { return nat; }
    public void setNat(String nat) { this.nat = nat; }
}
