package org.learning.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class User {
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户ID")
    private int id;
    private String username;
    private String password;
    private Integer userLevel;
    private String salt;
    private Long StudyTime;
    private LocalDateTime currentLogin;
}
