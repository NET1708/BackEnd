package com.swd391.backend.security;

public class Endpoints {
    public static final String url = "http://localhost:3000";

    public static final String prodUrl = "https://ani-testlab.edu.vn";

    public static final String apiUrl = "https://api.ani-testlab.edu.vn";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/swagger-ui/**",
            "/api-docs/**",
            "/course",
            "/course/**",
            "/image",
            "/image/**",
            "/user/search/existsByUsername",
            "/user/search/existsByEmail",
            "/account/activate",
            "/rate",
            "/rate/**",
            "/category",
            "/category/**",
            "/chapter",
            "/chapter/**",
            "/order/**",
            "/handle-payment",
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/login",
            "/rate",
            "/webhook",
            "/order/**"
    };

    public static final String[] PUBLIC_DELETE_ENDPOINTS = {
            "/account/register",
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/user",
            "/user/**",
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/course",
            "/course/**",
            "/image",
            "/image/**",
            "/category",
            "/category/**",
            "/playlist/videos",
    };
}
