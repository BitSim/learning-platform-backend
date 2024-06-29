package org.learning.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.learning.platform.dto.EduExamDTO;
import org.learning.platform.dto.EduQuestionDTO;
import org.learning.platform.entity.EduExam;

import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Mapper
public interface EduExamMapper extends BaseMapper<EduExam> {

}
