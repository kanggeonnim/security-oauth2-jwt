//package com.example.springsecurity3.util;
//
//import com.example.springsecurity3.dto.User;
//import org.springframework.security.core.context.SecurityContextHolder;
//
///*
//*  Security Context의 인증 객체로부터 다양한 정보를 뽑아서 제공하는 클래스
//* */
//public abstract class SecurityUtils {
//
//    public static String getUserId() {
//        return ((User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername();
//    }
//
//    public static User getUser() {
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
//}