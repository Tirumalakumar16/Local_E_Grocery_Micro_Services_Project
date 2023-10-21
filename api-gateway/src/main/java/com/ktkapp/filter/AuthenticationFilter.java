package com.ktkapp.filter;

import com.ktkapp.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
        @Autowired
        private RouteValidator validator;
        @Autowired
        private JwtUtil util;



        public AuthenticationFilter() {
            super(Config.class);
        }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->
        {
            ServerHttpRequest loggedInUser = null;
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!request.getHeaders().containsKey("Authorization")) {
                    throw new RuntimeException("missing authorization header ");
                }
                String authHeader = exchange.getRequest().getHeaders().get("Authorization").get(0);
//                System.out.println(authHeader);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    util.validateToken(authHeader);
                    loggedInUser = exchange.getRequest().mutate().header("LoggedInUser", util.extractUsername(authHeader))
                            .build();
                } catch (Exception e) {

                    throw new RuntimeException("un-Authorized access to application...Please sign-in to Application");
                }
            }
            return chain.filter(exchange.mutate().request(loggedInUser).build());
        }
                );
    }


    public static class Config{

    }
}
