package com.self.study.mapper;

import com.self.study.vo.UserVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolUserMapper {

    List<UserVo> findById(Long  id);

}
