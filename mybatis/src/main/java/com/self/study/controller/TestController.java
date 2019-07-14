package com.self.study.controller;

import com.self.study.service.SchoolUserService;
import com.self.study.service.UserService;
import com.self.study.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/test")
public class TestController {

    @Autowired
    private SchoolUserService userService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public UserVo getUser(@RequestParam(value = "id") Long id) {
        return userService.getUser(id);
    }

}