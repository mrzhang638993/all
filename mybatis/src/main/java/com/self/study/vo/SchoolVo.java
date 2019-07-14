package com.self.study.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SchoolVo {

    private   Integer  id;

    private  String   schoolName;


    private   String  schoolDescribe;

}
