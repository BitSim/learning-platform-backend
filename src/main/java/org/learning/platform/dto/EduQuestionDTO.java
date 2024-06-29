package org.learning.platform.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduQuestionDTO {
    @TableId(type = IdType.AUTO)
    @Schema(description = "问题id")
    private Integer id;
    @Schema(description = "问题描述")
    private String description;
    @Schema(description = "题目类型")
    private Integer type;
    @Schema(description = "答案")
    private String answer;
    @Schema(description = "选项")
    private String options;
}
