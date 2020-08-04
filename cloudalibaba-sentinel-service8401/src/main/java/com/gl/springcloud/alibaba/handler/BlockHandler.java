package com.gl.springcloud.alibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandler {

    public static String handlerException1(BlockException exception){
        return "被BlockException拦截到了........handlerException1";
    }

    public static String handlerException2(BlockException exception){
        return "被BlockException拦截到了........handlerException2";
    }
}
