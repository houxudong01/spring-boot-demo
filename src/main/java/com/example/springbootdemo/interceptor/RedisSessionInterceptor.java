package com.example.springbootdemo.interceptor;

import com.example.springbootdemo.config.MessageCodeConsts;
import com.example.springbootdemo.exception.UserNoticeException;
import com.example.springbootdemo.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author:hxd
 * @date:2020/5/31
 */
public class RedisSessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    private static final String USER_REGISTER_URL = "/api/user/register";

    private static final String USER_LOGIN_URL = "/api/user/login";

    private static final String ERROR_URL = "/api/error";

    private static final String USER_SESSION_KEY_PREFIX = "LOGIN_USER_ID:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().contains(USER_REGISTER_URL)
                || request.getRequestURI().contains(USER_LOGIN_URL)
                || request.getRequestURI().contains(ERROR_URL)) {
            return true;
        }

        //无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
        HttpSession session = request.getSession();

        if (session.getAttribute(USER_SESSION_KEY_PREFIX) == null) {
            throw new UserNoticeException(HttpStatus.NOT_FOUND.value(), MessageCodeConsts.NOT_FOUND_USER);
        }

        try {
            //验证当前请求的session是否是已登录的session
            String loginSessionId = (String) redisService.get(USER_SESSION_KEY_PREFIX + session.getAttribute(USER_SESSION_KEY_PREFIX).toString());
            if (loginSessionId != null && loginSessionId.equals(session.getId())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
