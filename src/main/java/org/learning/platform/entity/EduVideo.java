package org.learning.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduVideo {
    @TableId(type = IdType.AUTO)
    @Schema(description = "视频ID")
    private Integer id;
    @Schema(description = "章节ID")
    private Integer chapterId;
    @Schema(description = "课程ID")
    private Integer courseId;
    @Schema(description = "节点名称")
    private String title;
    @Schema(description = "视频地址")
    private String path;
    @Schema(description = "视频时长")
    private Long duration;
    @Schema(description = "创建时间")
    private String createTime;
    @Schema(description = "更新时间")
    private String updateTime;
}
