package com.valkyrie.api.model;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Api {

    private RouterFunction<ServerResponse> post(String name, String initialRoute, String finalRoute) {
        return route(name).POST(finalRoute,http()).before(uri(initialRoute)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return post("authentication", "http://localhost:8081", "/user/**");
    }

}
