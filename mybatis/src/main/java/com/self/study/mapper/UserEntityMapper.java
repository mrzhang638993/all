package com.self.study.mapper;


import com.self.study.domain.OrderEntity;
import com.self.study.domain.UserEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

//  使用继承Mapper 这些操作的话，不要使用自定义的方法的，存在问题的。
@Repository
public interface UserEntityMapper  extends Mapper<UserEntity> {


}