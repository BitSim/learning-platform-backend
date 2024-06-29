package org.learning.platform.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.learning.platform.entity.EduQuestion;

import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduExamDTO {
    @Schema(description = "试卷ID")
    private Integer id;
    @Schema(description = "title")
    private String title;
    @Schema(description = "试卷问题")
    private List<EduQuestion> questions;
}