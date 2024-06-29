package org.learning.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.learning.platform.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //新增用户
    void insertUser(User user);
    //查询用户密码
    User  selectUserByUsername(String username);

    @Update("update user set current_login=now() where id=#{id}")
    void updateCurrentLogin(int id);

    @Update("update user set study_time=study_time+#{studyTime} where id=#{id}")
    void updateStudyTime(@Param("id") int id,@Param("studyTime") Long studyTime);
}
