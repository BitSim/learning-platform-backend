package org.learning.platform.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.learning.platform.entity.EduVideo;

import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduCourseChapterDTO {
    @TableId(type = IdType.AUTO)
    @Schema(description = "章节ID")
    private Integer id;
    @Schema(description = "课程ID")
    private Integer courseId;
    @Schema(description = "课程名称")
    private String courseTitle;
    @Schema(description = "章节标题")
    private String title;
    @Schema(description = "课程图片")
    private String img;
    @Schema(description = "章节视频")
    private List<EduVideoDTO>eduVideos;
}
