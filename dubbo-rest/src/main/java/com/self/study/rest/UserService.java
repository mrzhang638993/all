package com.self.study.rest;

public interface UserService {

    //  注册
    String regist(User user);

    //  查询
    User queryById(Long id);
}