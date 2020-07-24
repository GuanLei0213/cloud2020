package com.gl.springcloud.alibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandler {

    public static String handlerException(BlockException exception){
        return "被BlockException拦截到了........";
    }
}
