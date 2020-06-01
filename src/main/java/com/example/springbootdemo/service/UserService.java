package com.example.springbootdemo.service;

import com.example.springbootdemo.config.MessageCodeConsts;
import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.exception.UserNoticeException;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final String CACHE_KEY_PREFIX = "user-";

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param birthday
     * @return
     * @throws JsonProcessingException
     */
    public User saveUser(String username, String password, Date birthday) throws JsonProcessingException {
        User user = new User(username, password, birthday);
        this.userMapper.save(user);
        this.redisService.set(CACHE_KEY_PREFIX + user.getId(), JsonUtil.writeValueAsString(user));
        return user;
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     */
    public User login(String username, String password, HttpServletRequest request) {
        User user = this.userMapper.getByUsername(username);
        if (user == null) {
            throw new UserNoticeException(HttpStatus.NOT_FOUND.value(), MessageCodeConsts.NOT_FOUND_USER);
        }
        String dbPassword = user.getPassword();
        // TODO 解密
        if (!dbPassword.equals(password)) {
            throw new UserNoticeException(HttpStatus.BAD_REQUEST.value(), MessageCodeConsts.USER_PASSWORD_INVALID);
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", user.getId());
        this.redisService.set("loginUser:" + user.getId(), session.getId());

        return user;
    }

    public User get(Long id) throws IOException {
        Object object = redisService.get(CACHE_KEY_PREFIX + id);
        if (object != null) {
            User user = JsonUtil.readValue(object.toString(), User.class);
            return user;
        }
        return this.userMapper.getById(id);
    }


    public List<User> listAll() {
        List<User> users = this.userMapper.listAll();
        logger.info("All users:{}", users);
        return users;
    }
}
