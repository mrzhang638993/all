package com.self.study.service;

import com.self.study.domain.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer  id);

    Integer  batchInsert(List<User> list)  throws Exception;
}
