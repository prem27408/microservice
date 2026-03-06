package com.api.gateway.server.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//to give custom eureka service name for postman
@Configuration
public class GatewayConfig {

    //id predicate filter and uri
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                 .route("category-servicee",route->route.path("/category/**")
                .filters(f->f.rewritePath("/category/?(?<remaining>.*)","/${remaining}")
                )
                .uri("lb://CATEGORY-SERVICEE")
                 )
                .route("quiz-service",route->route.path("/quiz/**")
                        .filters(f->f.rewritePath("/quiz/?(?<remaining>.*)","/${remaining}"))
                        .uri("lb://QUIZ-SERVICE")
                )
                .build();
    }
}
