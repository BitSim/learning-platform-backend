package org.learning.platform.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.*;
import org.learning.platform.entity.EduCourse;
import org.learning.platform.entity.EduCourseChapter;
import org.learning.platform.entity.EduVideo;
import org.learning.platform.mapper.EduCourseChapterMapper;
import org.learning.platform.mapper.EduCourseMapper;
import org.learning.platform.mapper.EduVideoMapper;
import org.learning.platform.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.learning.platform.dto.ResponseStatus.*;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Service
@Slf4j
public class EduCourseService {
    @Resource
    EduCourseMapper eduCourseMapper;

    @Resource
    EduVideoMapper eduVideoMapper;
    @Resource
    EduCourseChapterMapper eduCourseChapterMapper;
    @Resource
    EduVideoService eduVideoService;

    public ResponseResult getCourses(EduCourseDTO eduCourseDTO, int page, int size) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (eduCourseDTO.getTitle() != null) wrapper.like("title", eduCourseDTO.getTitle());
        if (eduCourseDTO.getDescription() != null) wrapper.like("description", eduCourseDTO.getDescription());
        if (eduCourseDTO.getPostStatus() != null) wrapper.eq("post_status", eduCourseDTO.getPostStatus());
//        if(UserHolder.getCurrentUser().getUserLevel()==0) wrapper.eq("post_status",true);
        Page<EduCourse> p = new Page<>(page, size);
        return ResponseResult.success(eduCourseMapper.selectList(p, wrapper));
    }

    /**
     * 创建课程
     *
     * @param eduCourseDTO 课程信息
     * @param file         图片信息
     * @return ResponseResult
     */
    public ResponseResult createCourse(EduCourseDTO eduCourseDTO, MultipartFile file) {
        ResponseResult res;
        try {
            res = FileUtil.updateAvatar(file);
            if (res.getCode() != 0) return res;
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseResult.error(ERROR);
        }
        eduCourseDTO.setImg((String) res.getData());
        eduCourseDTO.setPostStatus(false);
        eduCourseMapper.insertOrUpdate(BeanUtil.copyProperties(eduCourseDTO, EduCourse.class));
        return ResponseResult.success();
    }

    /**
     * 修改课程
     *
     * @param eduCourseDTO 课程信息
     * @param file
     * @return ResponseResult
     */
    public ResponseResult updateCourse(EduCourseDTO eduCourseDTO, MultipartFile file) {
        if (file != null) {
            ResponseResult res;
            try {
                res = FileUtil.updateAvatar(file);
                if (res.getCode() != 0) return res;
            } catch (IOException e) {
                log.error(e.getMessage());
                return ResponseResult.error(ERROR);
            }
        }
        eduCourseMapper.updateById(BeanUtil.copyProperties(eduCourseDTO, EduCourse.class));
        return ResponseResult.success();
    }

    /**
     * 发布课程
     *
     * @param eduCourseDTO 课程信息
     * @return ResponseResult
     */
    public ResponseResult publishCourse(EduCourseDTO eduCourseDTO) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(eduCourseDTO.getId()); // 设置你想要发布的课程的ID
        eduCourse.setPostStatus(eduCourseDTO.getPostStatus()); // 设置发布状态
        eduCourseMapper.updateById(eduCourse);
        return ResponseResult.success();
    }

    /**
     * 条件查询课程
     *
     * @param eduCourseDTO 课程信息
     * @return ResponseResult
     */
    public ResponseResult searchCourse(EduCourseDTO eduCourseDTO) {
        EduCourse searchCourse = BeanUtil.copyProperties(eduCourseDTO, EduCourse.class);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.setEntity(searchCourse);
        return ResponseResult.success(eduCourseMapper.selectList(wrapper));
    }

    /**
     * 删除课程
     *
     * @param id 课程ID
     * @return ResponseResult
     */
    public ResponseResult deleteCourse(int id) {
        eduVideoMapper.deleteByIds(List.of(id));
        eduCourseMapper.deleteById(id);
        QueryWrapper<EduCourseChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        eduCourseChapterMapper.delete(wrapper);
        return ResponseResult.success();
    }

    /**
     * 批量删除课程
     *
     * @param ids 课程ID
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult deleteCourses(int[] ids) {
        List<Integer> list = Arrays.stream(ids).boxed().toList();
        //批量删除章节
        eduVideoMapper.deleteByIds(list);
        QueryWrapper<EduCourseChapter> wrapper = new QueryWrapper<>();
        wrapper.in("course_id", list);
        eduCourseChapterMapper.delete(wrapper);
        eduCourseMapper.deleteBatchIds(list);
        return ResponseResult.success();
    }

    public ResponseResult getAllCourseDetail() {
        List<EduCourse> courses = eduCourseMapper.selectList(null);
        List<EduCourseChapter> chapters = eduCourseChapterMapper.selectList(null);
        List<EduVideo> videos = eduVideoMapper.selectList(null);

        Map<Integer, List<EduCourseChapter>> chaptersByCourseId = chapters.stream()
                .filter(chapter -> chapter.getCourseId() != null)
                .collect(Collectors.groupingBy(EduCourseChapter::getCourseId));

        Map<Integer, List<EduVideo>> videosByChapterId = videos.stream()
                .filter(video -> video.getChapterId() != null)
                .collect(Collectors.groupingBy(EduVideo::getChapterId));

        // 组装课程DTO
        return ResponseResult.success(courses.stream()
                .filter(EduCourse::getPostStatus)
                .map(course -> {
                    EduCourseDTO courseDTO = new EduCourseDTO();
                    BeanUtils.copyProperties(course, courseDTO);
                    List<EduCourseChapterDTO> chapterDTOs = chaptersByCourseId.getOrDefault(course.getId(), List.of()).stream()
                            .map(chapter -> {
                                EduCourseChapterDTO chapterDTO = new EduCourseChapterDTO();
                                BeanUtils.copyProperties(chapter, chapterDTO);

                                List<EduVideo> chapterVideos = videosByChapterId.getOrDefault(chapter.getId(), List.of());
                                chapterDTO.setEduVideos(
                                        chapterVideos.stream().map(video -> {
                                            EduVideoDTO videoDTO = new EduVideoDTO();
                                            BeanUtils.copyProperties(video, videoDTO);
                                            return videoDTO;
                                        }).collect(Collectors.toList())
                                );

                                return chapterDTO;
                            }).collect(Collectors.toList());

                    courseDTO.setEduCourseChapterDTOS(chapterDTOs);
                    return courseDTO;
                }).collect(Collectors.toList()));
    }
}
