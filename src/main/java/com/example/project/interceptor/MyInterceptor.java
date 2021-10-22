package com.example.project.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取url
        String requestURI = request.getRequestURI();
        // /role/toList -> role/toList
        String substring = requestURI.substring(1);
        // role/toList -> role
        int index = substring.indexOf("/");
        if (index != -1) {
            substring = substring.substring(0, index);
        }
        HashSet<String> urls = (HashSet<String>) request.getSession().getAttribute("model");
        // role是否存在 该用户所有有权限的资源中
        boolean match = urls.stream().anyMatch(substring::equals);
        if (!match) {
            response.sendRedirect("/");
        }
        return match;
    }
}
