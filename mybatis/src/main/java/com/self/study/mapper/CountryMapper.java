package com.self.study.mapper;

import com.self.study.domain.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CountryMapper {

    @Select("select * from country")
    List<Country> findAll();

}