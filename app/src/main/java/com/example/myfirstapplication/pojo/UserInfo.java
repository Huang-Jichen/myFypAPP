package com.example.myfirstapplication.pojo;

public class UserInfo {
    private int user_id;
    private String username;
    private String password;
    public static UserInfo UserInfos;

    public UserInfo() {
    }

    public UserInfo(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public static UserInfo getUserInfos() {
        return UserInfos;
    }

    public static void setUserInfos(UserInfo userInfos) {
        UserInfos = userInfos;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
