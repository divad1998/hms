//package com.ingryd.hms.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.catalina.filters.CorsFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class CorsFilterExt extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String value = response.getHeader("Access-Control-Allow-Origin");
//        if (value == null) {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            filterChain.doFilter(request, response);
//        }
//        filterChain.doFilter(request, response);
//    }
//}
