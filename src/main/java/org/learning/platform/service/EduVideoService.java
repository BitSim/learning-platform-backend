package org.learning.platform.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.learning.platform.constant.SystemConstant;
import org.learning.platform.dto.EduVideoDTO;
import org.learning.platform.dto.ResponseResult;
import org.learning.platform.dto.ResponseStatus;
import org.learning.platform.entity.EduVideo;
import org.learning.platform.mapper.EduVideoMapper;
import org.learning.platform.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Service
@Slf4j
public class EduVideoService {
    @Resource
    private EduVideoMapper eduVideoMapper;

    /**
     * 为章节添加视频
     *
     * @param eduVideoDTO 必须有ChapterID
     * @return ReponseResult
     */
    public ResponseResult addVideo(EduVideoDTO eduVideoDTO) {
        MultipartFile file=eduVideoDTO.getFile();
        ResponseResult responseResult;
        try {
            responseResult = FileUtil.updateVideo(file);
            if (responseResult.getCode() != 0) return responseResult;
            // 将MultipartFile保存到临时文件
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);
            // 使用ProcessBuilder执行FFmpeg命令
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-i", tempFile.getAbsolutePath(), "-f", "null", "-"
            );
            Process process = pb.start();
            // 读取FFmpeg的错误流输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // 解析FFmpeg输出中的时长信息
                if (line.contains("Duration:")) {
                    String durationStr = line.split(",")[0].split("Duration:")[1].trim();
                    eduVideoDTO.setDuration(FileUtil.parseDuration(durationStr));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseResult.error(ResponseStatus.ERROR);
        }
        eduVideoDTO.setPath((String) responseResult.getData());
        eduVideoMapper.insert(BeanUtil.copyProperties(eduVideoDTO, EduVideo.class));
        return ResponseResult.success();
    }

    /**
     * 批量删除视频
     *
     * @param ids
     * @return
     */
    public ResponseResult deleteVideos(int[] ids) {
        eduVideoMapper.deleteByIds(Arrays.stream(ids).boxed().toList());
        return ResponseResult.success();
    }

    public ResponseResult deleteVideo(EduVideoDTO eduVideoDTO) {
        UpdateWrapper<EduVideo> wrapper = new UpdateWrapper<>();
        wrapper.set("chapter_id", null).set("course_id",null).eq("id", eduVideoDTO.getId());
        eduVideoMapper.update(null, wrapper);
        return ResponseResult.success();
    }

    public ResponseResult deleteVideoFile(EduVideoDTO eduVideoDTO) {
        EduVideo video = eduVideoMapper.selectById(eduVideoDTO.getId());
        File file = new File(SystemConstant.COURSE_VIDEO_PATH+"/"+video.getPath());
        if (file.delete()) {
            eduVideoMapper.deleteById(eduVideoDTO.getId());
            return ResponseResult.success();
        } else {
            return ResponseResult.error(ResponseStatus.ERROR);
        }
    }
}
