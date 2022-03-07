package org.introduct.pcip.pcipdemo.filters;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class RequestContextFilter implements WebFilter {

    public static final String REQUEST_ID = "REQUEST_ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).contextWrite(context -> context.put(REQUEST_ID, exchange.getRequest().getId()));
    }
}
