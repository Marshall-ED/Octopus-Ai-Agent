package com.hezhu.octopusaiagent.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Marshall
 * @Date 2025/7/13 15:59
 * @Description:
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data,String message){
        this.code=code;
        this.data=data;
        this.message=message;
    }
    public BaseResponse(int code, T data){
        this.code = code;
        this.data = data;
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null,errorCode.getMessage());
    }
}
