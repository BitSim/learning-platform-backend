package org.learning.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduExamQuestion {
    @TableId(type = IdType.AUTO)
    @Schema(description = "表ID")
    private Integer id;
    @Schema(description = "试卷ID")
    private Integer examId;
    @Schema(description = "问题ID")
    private Integer questionId;

    public EduExamQuestion(Integer examId, Integer questionId) {
        this.examId=examId;
        this.questionId=questionId;
    }
}
