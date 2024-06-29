package org.learning.platform.service;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.dto.UserDTO;
import org.learning.platform.entity.User;
import org.learning.platform.mapper.UserMapper;
import org.learning.platform.util.PasswordUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Service
@Slf4j
public class UserService {
    @Resource
    UserMapper userMapper;

    public ResponseResult register(User user) {
        //防止爬虫持续伪造权限,只有管理员可以创建各种等级的用户
        user.setUserLevel(2);
        //TODO 用RSA密钥解密密码
        //加盐
        String salt = PasswordUtil.generateSalt();
        user.setPassword(PasswordUtil.hashPassword(user.getPassword(), salt));
        user.setSalt(salt);
        try {
            userMapper.insertUser(user);
        } catch (DuplicateKeyException e) {
            return ResponseResult.error(ResponseStatus.USER_ALREADY_EXIST);
        }
        return ResponseResult.success();
    }

    public ResponseResult login(User user, HttpServletRequest request) {
        //TODO 用RSA密钥解密密码
        User u = userMapper.selectUserByUsername(user.getUsername());
        if (u == null) return ResponseResult.error(ResponseStatus.USER_NOT_EXIST);
        if (!PasswordUtil.isExpectedPassword(user.getPassword(), u.getSalt(), u.getPassword()))
            return ResponseResult.error(ResponseStatus.USERNAME_OR_PASSWORD_ERROR);
        HttpSession session = request.getSession();  //获取session作用域
        //保存用户会话状态并设置有效时间为2h
        session.setAttribute("user", BeanUtil.copyProperties(u, UserDTO.class));
        session.setMaxInactiveInterval(2 * 60 * 60);
        log.info("用户{}登录成功", u.getUsername());
        //更新currentLogin为当前时间
        userMapper.updateCurrentLogin(u.getId());
        return ResponseResult.success();
    }

    public ResponseResult progress(User user) {
        //在study_time的字段增加时间
        userMapper.updateStudyTime(user.getId(), user.getStudyTime());
        return ResponseResult.success();

    }

    public ResponseResult getUserList() {
        return ResponseResult.success(userMapper.selectList(null));
    }
}
