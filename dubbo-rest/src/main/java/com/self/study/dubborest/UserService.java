package com.self.study.dubborest;

public interface UserService {

    //  注册
    String regist(User user);

    //  查询
    User queryById(Long id);
}