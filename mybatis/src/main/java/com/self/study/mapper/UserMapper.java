package com.self.study.mapper;


import com.self.study.domain.User;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    void doBatchInsert(List<User> list);
}
