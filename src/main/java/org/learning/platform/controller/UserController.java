package org.learning.platform.controller;

import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.entity.User;
import org.learning.platform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("/register")
    public ResponseResult register(User user) {
        //如果用户或密码为空
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseResult.error(ResponseStatus.USERNAME_OR_PASSWORD_NULL);
        return userService.register(user);
    }
    @PostMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request){
        //如果用户或密码为空
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseResult.error(ResponseStatus.USERNAME_OR_PASSWORD_NULL);
        return userService.login(user,request);

    }
}