package com.self.study.zookeeper.config;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<Integer>  list= new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer>  list1= new ArrayList<>();
        list1.add(4);
        list1.add(5);
        list1.add(6);
        list1.add(3);

        list.retainAll(list1);

        for(int  i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
