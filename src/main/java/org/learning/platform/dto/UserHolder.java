package org.learning.platform.dto;

import org.learning.platform.entity.User;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class UserHolder {
    private static final ThreadLocal<UserDTO> userDTOThreadLocal = ThreadLocal.withInitial(UserDTO::new);

    public static UserDTO getCurrentUser() {
        return userDTOThreadLocal.get();
    }

    public static void setCurrentUser(UserDTO userDTO) {
        userDTOThreadLocal.set(userDTO);
    }

    public static void remove() {
        userDTOThreadLocal.remove();
    }
}
