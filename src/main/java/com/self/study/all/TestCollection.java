package com.self.study.all;

import org.apache.commons.collections.set.SynchronizedSortedSet;

import java.util.*;

public class TestCollection {

    //  测试java中集合类的使用和操作信息
    public static void main(String[] args) {
        TestCollection testCollection = new TestCollection();
       // testCollection.testTreeMap();
        testCollection.testSortedSet();
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
}
