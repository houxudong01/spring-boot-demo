package com.example.springbootdemo.interceptor;

import com.example.springbootdemo.config.MessageCodeConsts;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.exception.UserNoticeException;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.RedisService;
import com.example.springbootdemo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:hxd
 * @date:2020/5/31
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    private static final String USER_REGISTER_URL = "/api/user/register";

    private static final String USER_LOGIN_URL = "/api/user/login";

    private static final String ERROR_URL = "/api/error";

    private static final String USER_TOKEN_KEY = "USER_TOKEN_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().contains(USER_REGISTER_URL)
                || request.getRequestURI().contains(USER_LOGIN_URL)
                || request.getRequestURI().contains(ERROR_URL)) {
            return true;
        }

        String token = request.getHeader(USER_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            throw new UserNoticeException(HttpStatus.UNAUTHORIZED.value(), MessageCodeConsts.USER_TOKEN_INVALID);
        }
        User user = this.userMapper.getByToken(token);
        String redisToken = (String) this.redisService.get(UserService.CACHE_KEY_PREFIX + user.getId());
        if (StringUtils.isBlank(redisToken)) {
            throw new UserNoticeException(HttpStatus.UNAUTHORIZED.value(), MessageCodeConsts.USER_TOKEN_INVALID);
        }

        return true;
    }
}
