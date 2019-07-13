package com.self.study;

import com.self.study.domain.User;
import com.self.study.service.UserService;
import com.self.study.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {



    @Test
    public void contextLoads() {
    }


    @Autowired
    private UserService userService;

    @Test
    public  void  test(){
        try {
            for (int i=0;i<100;i++) {
                userService.batchInsert(new ArrayList<>());
            }
        } catch (RuntimeException e) {
            System.out.println("捕获到数据库的操作异常信息");
           e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
      /* User userInfo = userService.findUserById(1);
        System.out.println(userInfo.toString());*/
       /*userService.findUserById(2);*/
    }

}
