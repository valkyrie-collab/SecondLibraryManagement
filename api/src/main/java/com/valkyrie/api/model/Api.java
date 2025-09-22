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

    private RouterFunction<ServerResponse> get(String name, String initialRoute, String finalRoute) {
        return route(name).GET(finalRoute,http()).before(uri(initialRoute)).build();
    }

    private RouterFunction<ServerResponse> delete(String name, String initialRoute, String finalRoute) {
        return route(name).DELETE(finalRoute,http()).before(uri(initialRoute)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return post("authentication", "http://authentication:8081", "/user/**")
            .and(
                get("entity-get", "http://entity:8082", "/entity/**")
            ).and(
                post("entity-post", "http://entity:8082", "/entity/**")
            ).and(
                delete("entity-post", "http://entity:8082", "/entity/**")
            );
    }

}
