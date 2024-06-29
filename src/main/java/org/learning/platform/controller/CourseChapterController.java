package org.learning.platform.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.EduCourseChapterDTO;
import org.learning.platform.dto.EduVideoDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.entity.EduCourseChapter;
import org.learning.platform.service.EduCourseChapterService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@Slf4j
@RequestMapping("/api/course/chapter")
public class CourseChapterController {
    @Resource
    private EduCourseChapterService eduCourseChapterService;
    /**
     * 添加课程章节
     * @param  eduCourseChapterDTOS 课程章节信息
     * @return ResponseResult
     */
    @PostMapping
    @Schema(description = "添加课程章节")
    public ResponseResult addChapter(@RequestBody List<EduCourseChapterDTO> eduCourseChapterDTOS){
        if(eduCourseChapterDTOS==null||eduCourseChapterDTOS.get(0).getCourseId()==null)
            return ResponseResult.error(ResponseStatus.PARAM_ERROR);
        return eduCourseChapterService.insertChapters(eduCourseChapterDTOS);
    }
    @GetMapping
    @Schema(description = "获取课程章节")
    public ResponseResult getChapter(EduCourseChapterDTO eduCourseChapterDTO) {
        return eduCourseChapterService.getChapter(eduCourseChapterDTO);
    }
    @DeleteMapping
    @Schema(description = "删除课程章节")
    public ResponseResult deleteChapter(@RequestBody  Map<String, List<Integer>> map) {
        //提取id转为List<Integer>
        return eduCourseChapterService.deleteChapter(map.get("ids"));
    }

    @GetMapping("/video")
    @Schema(description = "获取章节的视频列表")
    public ResponseResult getVideos(EduCourseChapterDTO eduCourseChapterDTO) {
        return eduCourseChapterService.getVideosByCourseChapterId(eduCourseChapterDTO);
    }
}
