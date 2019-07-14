package com.self.study;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.*;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.self.study.service.UserService;


import com.self.study.util.HttpClient;
import com.self.study.util.HttpClientResult;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
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


    //  下面使用httpclientutil来测试相关的httpclient的使用和操作的
    @Test
    public   void   testHttpClient() throws HttpProcessException {
        String url = "https://gitee.com/xutongle/HttpClient";
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.3")
                .accept("ext/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .acceptEncoding("gzip, deflate, br")
                .acceptLanguage("zh-CN,zh;q=0.9,en;q=0.8")
                .cookie("user_locale=zh-CN; oschina_new_user=false; aliyungf_tc=AQAAAFJ3uQMOlAUAVrF7d3jTBoIh9sgz; gitee-session-n=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTUzMWRhMWE2M2QwNzY3NDk4YWFjZWNlNDkyZTk3MjcxBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMXJ6Y0FGa3VyNnFrNS9UYnVGcTVxeWFxdkJUOWNmU2FLcEIya1BHUjNXcXc9BjsARg%3D%3D--9e71e6d6a223a95d09968832035201c4f66001b4; tz=Asia%2FShanghai; Hm_lvt_24f17767262929947cc3631f99bfd274=1562993239,1563027277,1563080321,1563080377; Hm_lpvt_24f17767262929947cc3631f99bfd274=1563080377")
               .host("gitee.com")
                .referer("https://gitee.com/xutongle/HttpClient")
                .other("customer", "自定义")
                .build();

        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
        HCB hcb = HCB.custom()
                .timeout(100000) //超时
                .pool(100, 10) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2) 	//设置ssl版本号，默认SSLv3，也可以调用sslpv("TLSv1.2")
               // .ssl()  	  	//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                .retry(5)		//重试5次
                ;

        org.apache.http.client.HttpClient client = hcb.build();

        Map<String, Object> map = new HashMap<String, Object>();
        /*map.put("source_action", "show");
        map.put("source_controller", "blob");*/

        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)	//设置headers，不需要时则无需设置
                .url(url)	          //设置请求的url
                .map(map)	          //设置请求参数，没有则无需设置
                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(client)    //如果只是简单使用，无需设置，会自动获取默认的一个client对象
                //.inenc("utf-8")  //设置请求编码，如果请求返回一直，不需要再单独设置
                //.inenc("utf-8")	//设置返回编码，如果请求返回一直，不需要再单独设置
                //.json("json字符串")                          //json方式请求的话，就不用设置map方法，当然二者可以共用。
                //.context(HttpCookies.custom().getContext()) //设置cookie，用于完成携带cookie的操作
                //.out(new FileOutputStream("保存地址"))       //下载的话，设置这个方法,否则不要设置
                //.files(new String[]{"d:/1.txt","d:/2.txt"}) //上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
                ;


        //使用方式：
      // String result1 = HttpClientUtil.get(config);     //get请求
       // String result2 = HttpClientUtil.post(config);    //post请求
       // System.out.println(result1);
        //System.out.println(result2);

        //HttpClientUtil.down(config);                   //下载，需要调用config.out(fileOutputStream对象)
        //HttpClientUtil.upload(config);                 //上传，需要调用config.files(文件路径数组)

        //如果指向看是否访问正常
        //String result3 = HttpClientUtil.head(config); // 返回Http协议号+状态码
        //int statusCode = HttpClientUtil.status(config);//返回状态码

        //[新增方法]sendAndGetResp，可以返回原生的HttpResponse对象，
        //同时返回常用的几类对象：result、header、StatusLine、StatusCode
        HttpResult respResult = HttpClientUtil.sendAndGetResp(config);
        System.out.println("返回结果：\n"+respResult.getResult());
        System.out.println("返回resp-header："+respResult.getRespHeaders());//可以遍历
        System.out.println("返回具体resp-header："+respResult.getHeaders("Date"));
        System.out.println("返回StatusLine对象："+respResult.getStatusLine());
        System.out.println("返回StatusCode："+respResult.getStatusCode());
        System.out.println("返回HttpResponse对象）（可自行处理）："+respResult.getResp());
    }

}
