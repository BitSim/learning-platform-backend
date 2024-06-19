package org.learning.platform.entity;

import lombok.Data;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@Data
public class User {
    private String username;
    private String password;
    private int userLevel;
    private String salt;
}
