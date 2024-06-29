package org.learning.platform.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.dto.*;
import org.learning.platform.entity.EduCourse;
import org.learning.platform.entity.EduCourseChapter;
import org.learning.platform.entity.EduVideo;
import org.learning.platform.mapper.EduCourseChapterMapper;
import org.learning.platform.mapper.EduCourseMapper;
import org.learning.platform.mapper.EduVideoMapper;
import org.learning.platform.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Service
@Slf4j
public class EduCourseChapterService {
    @Resource
    private EduCourseChapterMapper eduCourseChapterMapper;
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Resource
    private EduVideoMapper eduVideoMapper;

    /**
     * 为课程添加章节
     *
     * @param eduCourseChapterDTOS 章节信息
     * @return ResponseResult
     */
    public ResponseResult insertChapters(List<EduCourseChapterDTO> eduCourseChapterDTOS) {
        for(EduCourseChapterDTO eduCourseChapterDTO:eduCourseChapterDTOS){
            if(eduCourseChapterDTO.getCourseId()==null)
                return ResponseResult.error(ResponseStatus.PARAM_ERROR);
            if(eduCourseChapterDTO.getTitle()==null)
                return ResponseResult.error(ResponseStatus.PARAM_ERROR);
            eduCourseChapterMapper.insert(BeanUtil.copyProperties(eduCourseChapterDTO, EduCourseChapter.class));
        }
        return ResponseResult.success();
    }
//    public ResponseResult insertChapters(List<EduCourseChapterDTO> eduCourseChapterDTOS) {
//        for (EduCourseChapterDTO eduCourseChapterDTO : eduCourseChapterDTOS) {
//            eduCourseChapterMapper.insert(BeanUtil.copyProperties(eduCourseChapterDTO, EduCourseChapter.class));
//            for (EduVideoDTO video : eduCourseChapterDTO.getEduVideos()) {
//                MultipartFile file = video.getFile();
//                ResponseResult responseResult;
//                if (file != null) {
//                    try {
//                        responseResult = FileUtil.updateVideo(file);
//                        if(responseResult.getCode()==0) video.setPath(responseResult.getMessage());
//                        // 将MultipartFile保存到临时文件
//                        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
//                        file.transferTo(tempFile);
//                        // 使用ProcessBuilder执行FFmpeg命令
//                        ProcessBuilder pb = new ProcessBuilder(
//                                "ffmpeg", "-i", tempFile.getAbsolutePath(), "-f", "null", "-"
//                        );
//                        Process process = pb.start();
//                        // 读取FFmpeg的错误流输出
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            // 解析FFmpeg输出中的时长信息
//                            if (line.contains("Duration:")) {
//                                String durationStr = line.split(",")[0].split("Duration:")[1].trim();
//                                video.setDuration(FileUtil.parseDuration(durationStr));
//                            }
//                        }
//                    } catch (Exception e) {
//                        log.error("上传视频失败", e);
//                        return ResponseResult.error(ResponseStatus.ERROR);
//                    }
//                }
//                eduVideoMapper.insert(BeanUtil.copyProperties(video, EduVideo.class));
//            }
//        }
//        return ResponseResult.success();
//    }

    /**
     * 删除章节
     *
     * @param id 章节ID
     * @return ResponseResult
     */
    public ResponseResult deleteChapter(List<Integer> ids) {
        // 更新video的chapter_id，course_id为空
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.in("chapter_id", ids);
        List<EduVideo> videos = eduVideoMapper.selectList(wrapper);
        log.info(videos.toString());
        videos.forEach(video -> {
            UpdateWrapper<EduVideo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("chapter_id", null).set("course_id", null).eq("id", video.getId());
            eduVideoMapper.update(null, updateWrapper);
        });
        // 删除章节
        eduCourseChapterMapper.deleteByIds(ids);
        return ResponseResult.success();
    }

    /**
     * 获取课程章节
     *
     * @param eduCourseChapterDTO
     * @return
     */
    public ResponseResult getChapter(EduCourseChapterDTO eduCourseChapterDTO) {
        QueryWrapper<EduCourseChapter> wrapper = new QueryWrapper<>();
        Optional.ofNullable(eduCourseChapterDTO.getId()).ifPresent(id -> wrapper.eq("id", id));
        Optional.ofNullable(eduCourseChapterDTO.getCourseId()).ifPresent(courseId -> wrapper.eq("course_id", courseId));
        Optional.ofNullable(eduCourseChapterDTO.getTitle()).ifPresent(title -> wrapper.like("title", title));
        List<EduCourseChapter> chapters = eduCourseChapterMapper.selectList(wrapper);
        if (chapters.isEmpty()) {
            return ResponseResult.success();
        }
        List<EduCourseChapterDTO> chapterDTOs = chapters.stream().map(chapter -> {
            EduCourseChapterDTO dto = new EduCourseChapterDTO();
            BeanUtil.copyProperties(chapter, dto);
            EduCourse course = eduCourseMapper.selectById(chapter.getCourseId());
            dto.setCourseTitle(course.getTitle());
            dto.setImg(course.getImg());
            List<EduVideo> videos = eduVideoMapper.selectList(new QueryWrapper<EduVideo>().eq("chapter_id", chapter.getId()));
            dto.setEduVideos(videos.stream().map(video -> {
                EduVideoDTO videoDTO = new EduVideoDTO();
                BeanUtil.copyProperties(video, videoDTO);
                return videoDTO;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
        return ResponseResult.success(chapterDTOs);
    }

    public ResponseResult getVideosByCourseChapterId(EduCourseChapterDTO eduCourseChapterDTO) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        Optional.ofNullable(eduCourseChapterDTO.getCourseId()).ifPresent(courseId -> wrapper.eq("course_id", courseId));
        Optional.ofNullable(eduCourseChapterDTO.getId()).ifPresent(chapterId -> wrapper.eq("chapter_id", chapterId));
        List<EduVideo> videos = eduVideoMapper.selectList(wrapper);
        return ResponseResult.success(videos);
    }

}
