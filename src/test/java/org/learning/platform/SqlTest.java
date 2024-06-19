package org.learning.platform;

import org.junit.jupiter.api.Test;
import org.learning.platform.entity.User;
import org.learning.platform.mapper.UserMapper;
import org.learning.platform.service.UserService;
import org.learning.platform.util.PasswordUtil;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SqlTest {
    @Resource
    UserMapper userMapper;
    @Test
    @Transactional
    @Rollback(false)
    public void testMethod(){
        User user=new User();
        user.setPassword("1");
        user.setUsername("root");
        user.setUserLevel(1);
        String salt= PasswordUtil.generateSalt();
        user.setPassword(PasswordUtil.hashPassword(user.getPassword(),salt));
        user.setSalt(salt);
        userMapper.insertUser(user);

    }
    @Test
    public void testLogin(){
        User user=new User();
        user.setPassword("123");
        user.setUsername("testSalt");
        user.setUserLevel(2);
        User u = userMapper.selectUserByUsername(user.getUsername());
        System.out.println(PasswordUtil.isExpectedPassword(user.getPassword(), u.getSalt(),u.getPassword()));
    }
}
