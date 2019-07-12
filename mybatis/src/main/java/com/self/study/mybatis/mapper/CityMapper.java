package com.self.study.mybatis.mapper;

import com.self.study.mybatis.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {
    @Select("select id, name, state, country from city where state = #{state}")
    City findByState(@Param("state") String state);
}
