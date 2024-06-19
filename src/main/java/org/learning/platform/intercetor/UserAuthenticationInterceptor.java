package org.learning.platform.intercetor;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import org.learning.platform.dto.UserDTO;
import org.learning.platform.dto.UserHolder;
import org.learning.platform.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author BitSim
 * @version v1.0.0
 **/
@Slf4j
public class UserAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return true;
        HttpSession session = request.getSession(); //获取session作用域
        Object user = session.getAttribute("user");  // 获取session中的登陆标识
        if(user==null)  {
            log.info("用户未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return  false;
        }
        User user1 = (User) user;
        log.info("用户{}已登录", user1.getUsername());
        //全局操作
        BeanUtils.copyProperties(user1, UserHolder.getCurrentUser());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(handler instanceof HandlerMethod)) return;
        return;
    }
}
