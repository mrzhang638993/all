package com.self.study.dubboproducer.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import  tk.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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

    @Bean(name="mainPrimary")
    @Primary
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource  druidDataSource= new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_2?useUnicode=true&characterEncoding=utf8");
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

    @Bean(name = "sqlSessionFactoryBean")
    @Primary
    public SqlSessionFactoryBean  sqlSessionFactoryBean(@Qualifier("mainPrimary") DruidDataSource  druidDataSource){
        SqlSessionFactoryBean  sql= new SqlSessionFactoryBean();
        sql.setDataSource(druidDataSource);
       // Resource   resource= new ClassPathResource("classpath:mybatis/*.xml");
        //sql.setConfigLocation(resource);
        Resource[] list= new Resource[2];
        Resource   resource_1=new ClassPathResource("mybatis/UserEntityMapper.xml");
        Resource   resource_2=new ClassPathResource("mybatis/OrderEntityMapper.xml");
        list[0]=resource_1;
        list[1]=resource_2;
        //Resource[0]=resource_1;
        //Resource[1]=resource_2;
        sql.setMapperLocations(list);
        return   sql;
    }

    @Bean("mapperScannerConfigurer")
    @Primary
    public MapperScannerConfigurer mapperScannerConfigurer(@Qualifier("sqlSessionFactoryBean") SqlSessionFactoryBean  sqlSessionFactoryBean){
        MapperScannerConfigurer  mapperScannerConfigurer= new  MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.self.study.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return   mapperScannerConfigurer;

    }




}
