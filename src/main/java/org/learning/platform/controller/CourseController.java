package org.learning.platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.EduCourseDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.service.EduCourseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/api/course")
@Slf4j
public class CourseController {
    @Resource
    EduCourseService eduCourseService;

    @GetMapping
    public ResponseResult getCourses(
            @ModelAttribute EduCourseDTO eduCourseDTO,
            @RequestParam(required = false) int page, @RequestParam(required = false) int size
    )  {
        return eduCourseService.getCourses(eduCourseDTO,page, size);
    }

    @PostMapping
    @Operation(summary = "新增课程")
    public ResponseResult createCourse(@ModelAttribute EduCourseDTO eduCourseDTO, @RequestParam(value = "file",required = false) MultipartFile file) {
        return eduCourseService.createCourse(eduCourseDTO, file);
    }

    @PutMapping
    @Operation(summary = "修改课程")
    public ResponseResult updateCourse(@ModelAttribute EduCourseDTO eduCourseDTO, @RequestParam(value = "file",required = false) MultipartFile file) {
        log.info(eduCourseDTO.toString());
        return eduCourseService.updateCourse(eduCourseDTO, file);
    }


    @PostMapping("/publish")
    @Operation(summary = "发布或取消发布课程")
    public ResponseResult postCourse(@RequestBody EduCourseDTO eduCourseDTO) {
        return eduCourseService.publishCourse(eduCourseDTO);
    }

    /**
     * 批量删除课程
     * @param
     * @return
     */
    @DeleteMapping
    @Operation(summary = "批量删除课程")
    public ResponseResult deleteCourses(@RequestBody Map<String,int[]>map) {
        return eduCourseService.deleteCourses(map.get("ids"));
    }
    @GetMapping("/all/detail")
    @Operation(summary = "获取全部课程详情")
    public ResponseResult getAllCourseDetail() {
        return eduCourseService.getAllCourseDetail();
    }
}
