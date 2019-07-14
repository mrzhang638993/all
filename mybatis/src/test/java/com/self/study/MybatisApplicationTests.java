package com.self.study;

import com.self.study.domain.User;
import com.self.study.service.UserService;
import com.self.study.service.impl.UserServiceImpl;
import com.self.study.util.HttpClient;
import com.self.study.util.HttpClientResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testGet() throws Exception {
        HttpClientResult result = HttpClient.doGet("http://127.0.0.1:8080/hello/get");
        System.out.println(result);
    }

    /**
     * Description: 测试get带参请求
     *
     * @throws Exception
     */
    @Test
    public void testGetWithParam() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", "helloworld");
        HttpClientResult result = HttpClient.doGet("http://127.0.0.1:8080/hello/getWithParam", params);
        System.out.println(result);
    }

    /**
     * Description: 测试post带请求头不带请求参数
     *
     * @throws Exception
     */
    @Test
    public void testPost() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "123");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "application/json");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        HttpClientResult result = HttpClient.doPost("http://127.0.0.1:8080/hello/post", headers, null);
        System.out.println(result);
    }

    /**
     * Description: 测试post带参请求
     *
     * @throws Exception
     */
    @Test
    public void testPostWithParam() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", "0");
        params.put("message", "helloworld");
        HttpClientResult result = HttpClient.doPost("http://127.0.0.1:8080/hello/postWithParam", params);
        System.out.println(result);
    }

}
