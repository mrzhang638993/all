package com.self.study.mapper;

import com.self.study.domain.OrderEntity;

import  tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

@Resource(name = "orderScannerConfigurer")
public interface OrderEntityMapper extends Mapper<OrderEntity> {
}
