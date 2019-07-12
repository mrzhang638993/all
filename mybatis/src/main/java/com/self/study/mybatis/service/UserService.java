package com.self.study.mybatis.service;

import com.self.study.mybatis.domain.User;

public interface UserService {
    User findUserById(Integer  id);
}
