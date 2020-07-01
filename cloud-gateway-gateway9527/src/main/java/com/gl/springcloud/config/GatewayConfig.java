package com.gl.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /**
     * 以下为编码方式配置路由，常用的是通过yml文件的方式配置路由
     */
    @Bean
    public RouteLocator configRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_baidu",r -> r.path("/guonei")
                .uri("http://news/baidu.com/guonei")).build();
        return routes.build();
    }
}
