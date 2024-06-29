package org.learning.platform.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.learning.platform.dto.EduExamDTO;
import org.learning.platform.dto.EduQuestionDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.entity.EduExam;
import org.learning.platform.entity.EduExamQuestion;
import org.learning.platform.entity.EduQuestion;
import org.learning.platform.mapper.EduExamMapper;
import org.learning.platform.mapper.EduExamQuestionMapper;
import org.learning.platform.mapper.EduQuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Service
public class EduExamService {
    @Resource
    EduExamQuestionMapper eduExamQuestionMapper;
    @Resource
    EduQuestionMapper eduQuestionMapper;
    @Resource
    EduExamMapper eduExamMapper;
    public ResponseResult getExam(EduExamDTO eduExamDTO) {
        List<EduExam>eduExams = eduExamMapper.selectList(null);
        List<EduExamDTO> eduQuestionDTOS=new ArrayList<>();
        if(eduExamDTO == null) {
            eduQuestionDTOS = eduExams.stream().map(eduExam -> {
                EduExamDTO eduExamDTO1 = new EduExamDTO();
                eduExamDTO1.setId(eduExam.getId());
                eduExamDTO1.setTitle(eduExam.getTitle());
                Wrapper<EduExamQuestion> wrapper = new QueryWrapper<EduExamQuestion>().eq("exam_id", eduExam.getId());
                List<EduExamQuestion> eduExamQuestions = eduExamQuestionMapper.selectList(wrapper);
                List<EduQuestion> eduQuestions = eduExamQuestions.stream().map(eduExamQuestion -> {
                    return eduQuestionMapper.selectById(eduExamQuestion.getQuestionId());
                }).collect(Collectors.toList());
                eduExamDTO1.setQuestions(eduQuestions);
                return eduExamDTO1;
            }).collect(Collectors.toList());
        }
        return ResponseResult.success(eduQuestionDTOS);
    }

    public ResponseResult createExam(EduExamDTO eduExamDTO) {
        EduExam eduExam = new EduExam();
        eduExam.setTitle(eduExamDTO.getTitle());
        eduExamMapper.insert(eduExam);
        return ResponseResult.success();
    }

    @Transactional
    public ResponseResult generateExam(EduExam eduExam) {
        Integer examId = eduExam.getId();
        if(examId==null) {
            return ResponseResult.error(-1,"试卷ID不能为空");
        }
        //如果eduExamQuestionMapper有试卷就不能生成
        Wrapper<EduExamQuestion> wrapper = new QueryWrapper<EduExamQuestion>().eq("exam_id", examId);
        List<EduExamQuestion> eduExamQuestions = eduExamQuestionMapper.selectList(wrapper);
        if(eduExamQuestions.size()>0) {
            return ResponseResult.error(-1,"试卷已经生成");
        }
        Integer randomSingleChoice = eduQuestionMapper.randomSingleChoice();
        Integer randomJudge = eduQuestionMapper.randomJudge();
        Integer randomFill = eduQuestionMapper.randomFillBlank();
        EduExamQuestion eduExamQuestion = new EduExamQuestion(examId,randomSingleChoice);
        eduExamQuestionMapper.insert(eduExamQuestion);
        eduExamQuestion=new EduExamQuestion(examId,randomJudge);
        eduExamQuestionMapper.insert(eduExamQuestion);
        eduExamQuestion=new EduExamQuestion(examId,randomFill);
        eduExamQuestionMapper.insert(eduExamQuestion);

        return ResponseResult.success();

    }

    public ResponseResult getExamList() {
        List<EduExam> eduExams = eduExamMapper.selectList(null);
        return ResponseResult.success(eduExams);
    }

    public ResponseResult deleteExam(EduExam eduExam) {
        eduExamMapper.deleteById(eduExam.getId());
        //修改eduExamQuestion表
        Wrapper<EduExamQuestion> wrapper = new QueryWrapper<EduExamQuestion>().eq("exam_id", eduExam.getId());
        eduExamQuestionMapper.delete(wrapper);
        return ResponseResult.success();
    }
}
