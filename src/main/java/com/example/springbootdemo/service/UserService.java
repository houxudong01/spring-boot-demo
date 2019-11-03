package com.example.springbootdemo.service;

import com.example.springbootdemo.dao.UserDao;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
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
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    private static final String CACHE_KEY_PREFIX = "user-";

    public User saveUser(String username, String password, Date birthday) throws JsonProcessingException {
        User user = new User(username, password, birthday);
        this.userDao.save(user);
        this.redisService.set(CACHE_KEY_PREFIX + user.getId(), JsonUtil.writeValueAsString(user));
        return user;
    }

    public User get(Long id) throws IOException {
        Object object = redisService.get(CACHE_KEY_PREFIX + id);
        if (object != null) {
            User user = JsonUtil.readValue(object.toString(), User.class);
            return user;
        }
        return this.userDao.getById(id);
    }


    public List<User> listAll() {
        List<User> users = this.userDao.listAll();
        logger.info("All users:{}", users);
        return users;
    }
}
