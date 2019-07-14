package com.self.study.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo {
    private   Integer  id;

    private   String  userName;

    private   String description;
}
