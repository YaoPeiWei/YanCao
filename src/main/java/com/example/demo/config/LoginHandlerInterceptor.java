package com.example.demo.config;

import com.sun.istack.internal.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: mengxuegu
 * @description: 登录的拦截器。防止没有登录就进入个人主页
 * @author: Lunatic Princess
 * @create: 2019-04-09 22:51
 **/

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("user");
        if (loginUser != null) {
            //已经登陆过,放行
            return true;
        }else {
//            response.sendRedirect("/thymeleaf/userIndex/html");
            request.getRequestDispatcher("/thymeleaf/userIndex/html").forward(request, response);
            return false;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
