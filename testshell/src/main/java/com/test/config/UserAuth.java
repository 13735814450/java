package com.test.config;

/**
 * (类的描述)
 *
 * @author zhangh
 * @time 2021-03-26 16:12
 */
public class UserAuth {
    private String username;
    private String password;
    public UserAuth() {
    }
    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
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
