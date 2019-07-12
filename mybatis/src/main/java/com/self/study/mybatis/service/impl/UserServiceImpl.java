package com.self.study.mybatis.service.impl;


import com.self.study.mybatis.domain.User;
import com.self.study.mybatis.mapper.UserMapper;
import com.self.study.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zl on 2015/8/27.
 */

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private  UserMapper  userMapper;

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
