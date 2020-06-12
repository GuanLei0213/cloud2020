package com.gl.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private boolean success;

    private String message;

    private T      data;

    public CommonResult(boolean success,String message){
        this(success,message,null);
    }
}
