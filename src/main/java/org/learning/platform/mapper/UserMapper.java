package org.learning.platform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.learning.platform.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface UserMapper {
    //新增用户
    void insertUser(User user);
    //查询用户密码
    User  selectUserByUsername(String username);
}
