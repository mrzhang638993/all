package com.self.study.mybatis.domain;

/**
 * Created by zl on 2015/8/27.
 */
public class User {
    private Integer id;

    private String username;

    private String password;

    private Boolean make;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getMake() {
        return make;
    }

    public void setMake(Boolean make) {
        this.make = make;
    }
}
