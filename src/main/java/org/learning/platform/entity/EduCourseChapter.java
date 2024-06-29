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
public class EduCourseChapter {
    @TableId(type = IdType.AUTO)
    @Schema(description = "章节ID")
    private Integer id;
    @Schema(description = "课程ID")
    private Integer courseId;
    @Schema(description = "章节标题")
    private String title;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "更新时间")
    private String updateTime;
}
