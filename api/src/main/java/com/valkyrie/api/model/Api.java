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

    // @Bean
    // public RouterFunction<ServerResponse> routerFunction() {
    //     return post("authentication", "http://authentication:8081", "/user/**")
    //         .and(
    //             get("entity-get", "http://entity:8082", "/entity/**")
    //         ).and(
    //             post("entity-post", "http://entity:8082", "/entity/**")
    //         ).and(
    //             delete("entity-delete", "http://entity:8082", "/entity/**")
    //         ).and(
    //             get("book-get", "http://book:8084", "/book/**")
    //         ).and(
    //             post("book-post", "http://book:8084", "/book/**")
    //         ).and(
    //             delete("book-delete", "http://book:8084", "/book/**")
    //         ).and(
    //             get("fine-get", "http://fine:8085", "/fine/**")
    //         ).and(
    //             post("fine-post", "http://fine:8085", "/fine/**")
    //         ).and(
    //             delete("fine-delete", "http://fine:8085", "/fine/**")
    //         ).and(
    //             get("transaction-get", "http://transaction:8083", "/transaction/**")
    //         ).and(
    //             post("transaction-post", "http://transaction:8083", "/transaction/**")
    //         ).and(
    //             delete("transaction-delete", "http://transaction:8083", "/transaction/**")
    //         );
    // }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionTwo() {
        return post("authentication", "http://localhost:8081", "/user/**")
            .and(
                get("entity-get", "http://localhost:8082", "/entity/**")
            ).and(
                post("entity-post", "http://localhost:8082", "/entity/**")
            ).and(
                delete("entity-delete", "http://localhost:8082", "/entity/**")
            ).and(
                get("book-get", "http://localhost:8084", "/book/**")
            ).and(
                post("book-post", "http://localhost:8084", "/book/**")
            ).and(
                delete("book-delete", "http://localhost:8084", "/book/**")
            ).and(
                get("fine-get", "http://localhost:8085", "/fine/**")
            ).and(
                post("fine-post", "http://localhost:8085", "/fine/**")
            ).and(
                delete("fine-delete", "http://localhost:8085", "/fine/**")
            ).and(
                get("transaction-get", "http://localhost:8083", "/transaction/**")
            ).and(
                post("transaction-post", "http://localhost:8083", "/transaction/**")
            ).and(
                delete("transaction-delete", "http://localhost:8083", "/transaction/**")
            );
    }
}
