package org.learning.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduCourse {
    @TableId(type = IdType.AUTO)
    @Schema(description = "课程ID")
    private Integer id;
    @Schema(description = "课程标题")
    private String title;
    @Schema(description = "课程图片路径")
    private String img;
    @Schema(description = "更新时间")
    private String updateTime;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "总课时")
    private LocalDateTime hour;
    @Schema(description = "课程描述")
    private String description;
    @Schema(description = "发布状态，0未发布，1已发布")
    private Boolean postStatus;
}


