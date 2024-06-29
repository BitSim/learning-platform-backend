package org.learning.platform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.learning.platform.entity.EduExamQuestion;

import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface EduExamQuestionMapper extends BaseMapper<EduExamQuestion> {
}
