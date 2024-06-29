package org.learning.platform.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.learning.platform.dto.EduExamDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.entity.EduExam;
import org.learning.platform.service.EduExamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Resource
    EduExamService eduExamService;
    @GetMapping
    @Schema(description = "获取试卷")
    public ResponseResult getExam(@RequestBody(required = false) EduExamDTO eduExamDTO){
        return eduExamService.getExam(eduExamDTO);
    }
    @PostMapping
    @Schema(description = "创建试卷")
    public ResponseResult generateExam(@RequestBody EduExamDTO eduExamDTO){
        return eduExamService.createExam(eduExamDTO);
    }
    @PostMapping("/generate")
    @Schema(description = "根据Id生成题目")
    public ResponseResult generateExam(@RequestBody EduExam eduExam){
        return eduExamService.generateExam(eduExam);
    }

    @GetMapping("/list")
    @Schema(description = "获取试卷列表")
    public ResponseResult getExamList(){
        return eduExamService.getExamList();
    }
    @DeleteMapping
    @Schema(description = "删除试卷")
    public ResponseResult deleteExam(@RequestBody EduExam eduExam){
        return eduExamService.deleteExam(eduExam);
    }
}
