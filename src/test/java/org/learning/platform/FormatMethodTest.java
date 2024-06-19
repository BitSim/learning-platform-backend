package org.learning.platform;

import org.junit.jupiter.api.Test;
import org.learning.platform.dto.UserHolder;
import org.learning.platform.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class FormatMethodTest {
    @Test
    public void smallTest(){
        User user=new User();
        user.setUsername("1");
        BeanUtils.copyProperties(user, UserHolder.getCurrentUser());
        System.out.println(UserHolder.getCurrentUser());
    }
}
