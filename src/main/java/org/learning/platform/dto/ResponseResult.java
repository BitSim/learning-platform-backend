package org.learning.platform.dto;


public class ResponseResult {

    private Integer code;
    private String message;
    private Object data;

    private ResponseResult() {
    }

    private ResponseResult(ResponseStatus responseStatus, Object data) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getDescription();
        this.data = data;
    }
    private ResponseResult(Integer code,String message,Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static ResponseResult success(){
        return new ResponseResult(ResponseStatus.SUCCESS,null);
    }

    public  static ResponseResult success(Object data){
        return new ResponseResult(ResponseStatus.SUCCESS,data);
    }
    public static ResponseResult error(Integer code,String message){
        return new ResponseResult(code,message,null);
    }
    public static ResponseResult error(ResponseStatus responseStatus){
        return new ResponseResult(responseStatus,null);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
