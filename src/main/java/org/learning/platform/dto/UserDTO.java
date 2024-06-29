package org.learning.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class UserDTO {
    private int id;
    private String username;
    private Integer userLevel;
    private Long StudyTime;
    private LocalDateTime currentLogin;
}