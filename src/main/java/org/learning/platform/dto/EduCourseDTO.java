package org.learning.platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduCourseDTO {
    @Schema(description = "课程ID")
    private Integer id;
    @Schema(description = "课程标题")
    private String title;
    @Schema(description = "课程图片路径")
    private String img;
    @Schema(description = "总课时")
    private LocalDateTime hour;
    @Schema(description = "课程描述")
    private String description;
    @Schema(description = "发布状态，0未发布，1已发布")
    private Boolean postStatus;
    @Schema(description = "课程章节")
    private List<EduCourseChapterDTO>eduCourseChapterDTOS;
}
