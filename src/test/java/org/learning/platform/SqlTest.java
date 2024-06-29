package org.learning.platform;

import org.junit.jupiter.api.Test;
import org.learning.platform.entity.EduCourse;
import org.learning.platform.entity.User;
import org.learning.platform.mapper.EduCourseMapper;
import org.learning.platform.mapper.UserMapper;
import org.learning.platform.service.UserService;
import org.learning.platform.util.PasswordUtil;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author BitSim
 * @version v1.0.0
 **/
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SqlTest {
    @Resource
    EduCourseMapper eduCourseMapper;
    @Test
    @Transactional
    @Rollback(false)
    public void testMethod(){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setTitle("数据库");
        eduCourseMapper.insert(eduCourse);

    }
    @Test
    public void testLogin(){

    }
}
