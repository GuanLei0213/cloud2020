package com.gl.springcloud.filter;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component //让SpringBoot容器扫描到
@Slf4j
public class LogGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********************** come in LogGatewayFilter......time:{}", DateTime.now());
        String userName = exchange.getRequest().getQueryParams().getFirst("userName");
        if (StringUtils.isEmpty(userName)){
            log.warn("********************** userName为空，非法用户！");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 加载过滤器顺序，返回的数值越小，优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
