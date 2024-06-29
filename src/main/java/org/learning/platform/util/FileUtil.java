package org.learning.platform.util;

import cn.hutool.core.io.FileTypeUtil;
import org.learning.platform.constant.SystemConstant;
import org.learning.platform.dto.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.learning.platform.dto.ResponseStatus.*;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class FileUtil {

    public static ResponseResult updateAvatar(MultipartFile file) throws IOException {
        //非空判断
        if (file == null) return ResponseResult.error(AVATAR_NOT_NULL);
        // 图片大小限制在 2MB
        if (file.getSize() > 2097152L) return ResponseResult.error(AVATAR_SIZE_LIMIT);
        String type = FileTypeUtil.getType(file.getInputStream());
        // 判断是否目标格式图片文件
        if (!("jpg".equals(type) || "jpeg".equals(type) || "png".equals(type)))
            return ResponseResult.error(AVATAR_TYPE_ERROR);
        //存到/resources/images/course_avatar
        String fileName = UUID.randomUUID() + "." + type;
        File saveFile = new File(SystemConstant.COURSE_AVATAR_PATH + "/" + fileName);
        //如果父级目录不存在则逐一创建
        if (!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
        file.transferTo(saveFile);
        return ResponseResult.success(fileName);
    }
    public static ResponseResult updateVideo(MultipartFile file) throws IOException {
        //非空判断
        if (file == null) return ResponseResult.error(AVATAR_NOT_NULL);
        String type = FileTypeUtil.getType(file.getInputStream());
        // 判断是否目标是否是视频格式
        if (!("mp4".equals(type) || "avi".equals(type) || "flv".equals(type)))
            return ResponseResult.error(VIDEO_TYPE_ERROR);
        //存到/resources/videos/course_video
        String fileName = UUID.randomUUID() + "." + type;
        File saveFile = new File(SystemConstant.COURSE_VIDEO_PATH + "/" + fileName);
        //如果父级目录不存在则逐一创建
        if (!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
        file.transferTo(saveFile);
        return ResponseResult.success(fileName);
    }
    public static long parseDuration(String duration) {
        String[] parts = duration.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        return hours * 3600 + minutes * 60 + (long) seconds;
    }



}
