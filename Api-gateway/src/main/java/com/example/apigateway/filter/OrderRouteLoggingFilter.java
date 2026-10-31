package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
@Slf4j
public class OrderRouteLoggingFilter extends AbstractGatewayFilterFactory<OrderRouteLoggingFilter.Config> {


    public OrderRouteLoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Logging from OrderRouteLoggingFilter pre {}", exchange.getRequest().getURI());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(()->{
                        log.info("Logging from OrderRouteLoggingFilter post {}", exchange.getRequest().getURI());
                    })
            );
        };
    }

    public static  class Config{

    }
}
