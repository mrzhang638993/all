package com.self.study.mapper;


import com.self.study.domain.OrderEntity;
import com.self.study.domain.UserEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


@Repository
public interface UserEntityMapper  extends Mapper<UserEntity> {


}