package com.devstack.pos.dto;

public class UserDto {
    private String Email;
    private String password;

    public UserDto() {
    }

    public UserDto(String email, String password) {
        Email = email;
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
