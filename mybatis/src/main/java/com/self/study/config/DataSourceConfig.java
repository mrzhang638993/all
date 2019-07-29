package com.self.study.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
//@ImportResource("jdbc.properties")
public class DataSourceConfig {


    //  分页插件配置
    @Bean
    public PageHelper  pageHelper(){
        PageHelper  pageHelper= new PageHelper();
        Properties  properties= new Properties();
        properties.setProperty("dialect","mysql");
        properties.setProperty("reasonable","true");
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("pageSizeZero","true");
        pageHelper.setProperties(properties);
        return   pageHelper;
    }

    @Bean
    @Primary
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource  druidDataSource= new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("dbc:mysql://localhost:3306/db_1?useUnicode=true&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        druidDataSource.setFilters("stat");
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMaxIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(3000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        List<String>  listSqls=new ArrayList<>();
        listSqls.add("set names utf8mb4;");
        druidDataSource.setConnectionInitSqls(listSqls);
        return   druidDataSource;
    }

    @Bean
    @ConditionalOnClass(DruidDataSource.class)
    public SqlSessionFactoryBean  sqlSessionFactoryBean(DruidDataSource  druidDataSource){
        SqlSessionFactoryBean  sql= new SqlSessionFactoryBean();
        sql.setDataSource(druidDataSource);
        Resource   resource= new ClassPathResource("");
        sql.setConfigLocation(resource);
        return   sql;
    }

    @Bean
   @ConditionalOnClass(SqlSessionFactoryBean.class)
    public MapperScannerConfigurer mapperScannerConfigurer(SqlSessionFactoryBean  sqlSessionFactoryBean){
        MapperScannerConfigurer  mapperScannerConfigurer= new  MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.self.study.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("org.mybatis.spring.SqlSessionFactoryBean");
        return   mapperScannerConfigurer;

    }




}
