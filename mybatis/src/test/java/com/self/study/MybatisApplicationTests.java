package com.self.study;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.self.study.domain.OrderEntity;
import com.self.study.domain.UserEntity;
import com.self.study.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.KeyGenerator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
//import tk.mybatis.spring.annotation.MapperScan;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {



    @Test
    public void contextLoads() {
    }


  /*  @Autowired
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
*/

    @Autowired
    private UserService  service;

    @Test
    public     void  testUserService(){
        for (int i = 1; i < 100; i++) {
            UserEntity user = service.getUser(i);
            if (user != null) {
                System.out.println(user);
            } else {
                user = new UserEntity(i, "User-" + i);
                service.addUser(user);
            }
        }


        for (int i = 1; i < 5; i++) {
            OrderEntity order = service.getOrder(i);
            if (order != null) {
                System.out.println(order);
            } else {
                order = new OrderEntity("NO-" + i);
                Random  random= new Random();
                int key = random.nextInt(1000000);
                order.setOrderId(key);
                service.addOrder(order);
            }
        }
    }


    @Autowired
    private   PageHelper  pageHelper;
    @Test
    public  void  testPage(){
        //  实现分页查找的操作实现逻辑
        PageHelper.startPage(1, 9);
        List<UserEntity>list=service.selectAll();
        PageInfo<UserEntity> pageInfo = new PageInfo<>(list);
        pageInfo.getList().forEach(x->{
            System.out.println(x.getUserId());
        });
    }

}
