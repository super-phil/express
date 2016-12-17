package com.magic.express.controller;

import com.magic.express.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 登录
     *
     * @param username username
     * @param password password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.login(username, password);
    }
}
