package org.learning.platform.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.learning.platform.dto.EduVideoDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.service.EduVideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/api/course/video")
public class VideoController {
    @Resource
    private EduVideoService eduVideoService;


    @PostMapping
    @Schema(description = "为章节添加视频")
    public ResponseResult addVideo(EduVideoDTO eduVideoDTO){
        if (eduVideoDTO.getChapterId() == 0 || eduVideoDTO.getTitle() == null
                || "".equals(eduVideoDTO.getTitle()))
            return ResponseResult.error(ResponseStatus.PARAM_ERROR);
        return eduVideoService.addVideo(eduVideoDTO);
    }


    @DeleteMapping
    @Schema(description = "删除视频关联的章节")
    public ResponseResult deleteVideo(@RequestBody EduVideoDTO eduVideoDTO){
        if (eduVideoDTO.getId() == 0)
            return ResponseResult.error(ResponseStatus.PARAM_ERROR);
        return eduVideoService.deleteVideo(eduVideoDTO);
    }
    @DeleteMapping("/file")
    @Schema(description = "删除视频文件")
    public ResponseResult deleteVideoFile(@RequestBody EduVideoDTO eduVideoDTO){
        if (eduVideoDTO.getId() == 0)
            return ResponseResult.error(ResponseStatus.PARAM_ERROR);
        return eduVideoService.deleteVideoFile(eduVideoDTO);
    }
}
