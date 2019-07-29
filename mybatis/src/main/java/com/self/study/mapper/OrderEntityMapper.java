package com.self.study.mapper;

import com.self.study.domain.OrderEntity;

import com.self.study.domain.UserEntity;
import org.springframework.stereotype.Repository;
import  tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;




//@Resource(name = "orderScannerConfigurer")
@Repository
public interface OrderEntityMapper  extends  Mapper<OrderEntity> {
}
