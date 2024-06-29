package org.learning.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.learning.platform.entity.EduCourse;

import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse>{
}
