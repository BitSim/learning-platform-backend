package org.learning.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.learning.platform.entity.EduQuestion;

import java.util.List;
import java.util.Queue;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface EduQuestionMapper extends BaseMapper<EduQuestion>{
    @Select("SELECT id FROM edu_question where type='0' ORDER BY RAND() LIMIT 1 ")
    Integer randomSingleChoice();
    @Select("SELECT id FROM edu_question where type='1' ORDER BY RAND() LIMIT 1 ")
    Integer randomFillBlank();
    @Select("SELECT id FROM edu_question where type='2' ORDER BY RAND() LIMIT 1 ")
    Integer randomJudge();
}
