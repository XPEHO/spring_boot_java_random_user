package com.xpeho.spring_boot_java_random_user.domain.entities;

import org.springframework.data.annotation.Id;

public class UserEntity {
    @Id
    private Long id;
    private String gender;
    private String firstname;
    private String lastname;
    private String civility;
    private String email;
    private String phone;
    private String picture;
    private String nat;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getCivility() { return civility; }
    public void setCivility(String civility) { this.civility = civility; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
    public String getNat() { return nat; }
    public void setNat(String nat) { this.nat = nat; }
}
