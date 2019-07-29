package com.self.study.service.impl;

import com.self.study.domain.OrderEntity;
import com.self.study.domain.UserEntity;
import com.self.study.mapper.OrderEntityMapper;
import com.self.study.mapper.UserEntityMapper;
import com.self.study.service.UserService;
import com.self.study.utils.DBContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl   implements UserService {

    @Autowired
    UserEntityMapper userEntityMapper;

    @Autowired
    OrderEntityMapper orderEntityMapper;

    @Override
    public void addUser(UserEntity userEntity) {
        //switch db
        DBContext.setDBKey(DBContext.getDBKeyByUserId(userEntity.getUserId()));
        userEntityMapper.insertSelective(userEntity);
    }

    @Override
    public UserEntity getUser(int userId) {
        //switch db
        DBContext.setDBKey(DBContext.getDBKeyByUserId(userId));
        return userEntityMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void addOrder(OrderEntity orderEntity) {
        //since orderEntityMapper can auto switch db by annotation
        //so we don't need to switch db manually
        orderEntityMapper.insertSelective(orderEntity);
    }

    @Override
    public OrderEntity getOrder(int orderId) {
        //since orderEntityMapper can auto switch db by annotation
        //so we don't need to switch db manually
        return orderEntityMapper.selectByPrimaryKey(orderId);
    }
}
