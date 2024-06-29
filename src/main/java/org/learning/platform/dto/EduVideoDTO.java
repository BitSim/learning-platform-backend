package org.learning.platform.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class EduVideoDTO {
    @TableId(type = IdType.AUTO)
    @Schema(description = "视频ID")
    private int id;
    @Schema(description = "章节ID")
    private int chapterId;
    @Schema(description = "课程ID")
    private int courseId;
    @Schema(description = "节点名称")
    private String title;
    @Schema(description = "视频地址")
    private String path;
    @Schema(description = "视频时长")
    private Long duration;
    @Schema(description = "视频资源")
    private MultipartFile file;
}
