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
public class EduExam {
    @TableId(type = IdType.AUTO)
    @Schema(description = "试卷ID")
    private Integer id;
    @Schema(description = "title")
    private String title;
}
