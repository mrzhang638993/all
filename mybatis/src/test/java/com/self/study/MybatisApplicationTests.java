package com.self.study;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.self.study.domain.Country;
import com.self.study.mapper.CountryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.self.study.mapper")
public class MybatisApplicationTests {



    @Test
    public void contextLoads() {
    }


    @Autowired
    private CountryMapper  countryMapper;


    @Test
    public void run() throws Exception {
        PageHelper.startPage(1, 20);
        List<Country> countries = countryMapper.findAll();
        System.out.println("Total: " + ((Page) countries).getTotal());
        for (Country country : countries) {
            System.out.println("Country Name: " + country.getCountryname());
        }
    }

}
