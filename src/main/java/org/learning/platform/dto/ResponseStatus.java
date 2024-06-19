package org.learning.platform.dto;



public enum ResponseStatus {
    SUCCESS(0,"success"),
    USERNAME_OR_PASSWORD_ERROR(1,"用户名或密码错误"),
    USER_NOT_EXIST(2,"用户不存在"),
    USER_ALREADY_EXIST(3,"用户已存在"),
    USER_NOT_LOGIN(4,"用户未登录"),
    USERNAME_OR_PASSWORD_NULL(5,"用户名或密码为空"),
    ERROR(400,"请求参数错误"),;
    private final Integer code;
    private final String description;
    private ResponseStatus(Integer code,String description){
        this.code=code;
        this.description=description;
    }
    public Integer getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }
}
