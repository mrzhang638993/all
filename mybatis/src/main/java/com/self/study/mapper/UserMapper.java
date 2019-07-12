package com.self.study.mapper;


import com.self.study.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by zl on 2015/8/27.
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
