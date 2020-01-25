package com.online.edu.eduservice.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        final String authHeader = request.getHeader("X-Token");
        if (authHeader != null ) {
            final String token = authHeader;
            // The part after "Bearer "
            Claims claims = jwtUtil.parseJWT(token);
            System.out.println("111111111111");
            System.out.println(claims.get("roles"));
            if (claims != null) {
                if ("[admin]".equals(claims.get("roles"))) {//如果是管理员
                    request.setAttribute("admin_claims", claims.get("roles"));
                    System.out.println(22222);
                }
                if ("[guest]".equals(claims.get("roles"))) {//如果是用户
                    request.setAttribute("user_claims", claims.get("roles"));
                }
            }
        }
        return true;
    }
}
