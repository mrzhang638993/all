package com.self.study.all;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.springframework.beans.propertyeditors.ReaderEditor;

import java.util.*;

public class TestCollection {

    //  测试java中集合类的使用和操作信息,大多数的数据的这种问题的话,一般的可以根据相关的collections就可以获取到相关的元素的
    public static void main(String[] args) {
        TestCollection testCollection = new TestCollection();
       // testCollection.testTreeMap();
       // testCollection.testSortedSet();
        //testCollection.testSynSet();
        testCollection.testEnumMap();
    }

    public   void   testTreeMap() {
        TreeMap map = new TreeMap<String, String>(new Comparator<String>() {
            //  根据字符串的排序规则进行相关的排序(执行的是降序排序的数据)
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        // 执行排序操作之后,对应的key按照相关的递增的顺序来实现相关的排序
        map.put("mrzhang", "hello   world");
        map.put("hello", "coming  again");
        //  排序之前的操作实现
        map.keySet().forEach((key) -> {
            System.out.println(key);
            System.out.println(map.get(key));
        });
    }

        public   void   testSortedSet(){
          // 实现set的排序输出操作和实现逻辑
            Set   set= new HashSet<>();
            set.add("mrzhang");
            set.add("success");
            set.add("failed");
            Set sets = new TreeSet(set);
            sets.forEach(x->{
                System.out.println(x);
            });
        }

        public   void   testSynSet(){
            Set   set= new HashSet<>();
            set.add("good");
            set.add("morning");
            set.add("tonight");
            //  返回线程安全的set
            Set sets = Collections.synchronizedSet(set);
            sets.forEach(x->{
                System.out.println(x);
            });
        }

          public   void   testEnumMap(){
             // 使用Enum作为key,类似的EnumSet对应的是采用相同的机制的
             EnumMap   enumMap= new EnumMap(Color.class);
              enumMap.put(Color.RED,"red");
              enumMap.put(Color.BLACK,"black");
             enumMap.keySet().forEach(x->{
                 System.out.println(x);
                 System.out.println(enumMap.get(x));
             });
             //  测试对应的enumMap的接口的api的相关实现
              enumMap.put(Color.YELLOW,"yellow");

              enumMap.keySet().forEach(x->{
                  System.out.println(x);
                  System.out.println(enumMap.get(x));
              });
          }


          public  enum   Color{
              RED("red","1"),
              YELLOW("yellow","2"),
              BLACK("black","3");


             private String  msg;
             private  String key ;

             private  Color(String msg,String key){
                 this.msg=msg;
                 this.key=key;
             }



        }

}
