package com.self.study.service;

import com.self.study.domain.OrderEntity;
import com.self.study.domain.UserEntity;

public interface UserService {

    void addUser(UserEntity userEntity);

    UserEntity getUser(int userId);

    void addOrder(OrderEntity orderEntity);

    OrderEntity getOrder(int orderId);

}
