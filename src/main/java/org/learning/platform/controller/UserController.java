package org.learning.platform.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.entity.User;
import org.learning.platform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("/register")
    @Schema(description = "用户注册")
    public ResponseResult register(@RequestBody User user) {
        //如果用户或密码为空
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseResult.error(ResponseStatus.USERNAME_OR_PASSWORD_NULL);
        return userService.register(user);
    }
    @PostMapping("/login")
    @Schema(description = "用户登录")
    public ResponseResult login(@RequestBody User user, HttpServletRequest request){
        //如果用户或密码为空
        if (user.getUsername() == null || user.getPassword() == null)
            return ResponseResult.error(ResponseStatus.USERNAME_OR_PASSWORD_NULL);
        return userService.login(user,request);
    }
    @GetMapping("/currentUser")
    @Schema(description = "获取用户的信息")
    public ResponseResult currentUser(HttpServletRequest request){
        return ResponseResult.success(request.getSession().getAttribute("user"));
    }

    @Schema(description = "学习进度")
    @PostMapping("/progress")
    public ResponseResult progress(@RequestBody User user){
        return userService.progress(user);
    }
    @GetMapping
    @Schema(description = "获取用户列表")
    public ResponseResult getUserList(){
        return userService.getUserList();
    }
}