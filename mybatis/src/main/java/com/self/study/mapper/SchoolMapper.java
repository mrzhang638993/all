package com.self.study.mapper;

import com.self.study.vo.SchoolVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMapper {

    List<SchoolVo> findByName(String name);
}
