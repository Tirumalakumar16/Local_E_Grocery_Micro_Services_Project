package com.ktkapp.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {


    public static final List<String> openEndPoints = Arrays.asList(
            "/www.localGrocery.com/identity/api/register",
            "/eureka",
            "/www.localGrocery.com/identity/api/sign_in",
            "/www.localGrocery.com/identity/api/resetpassword"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
