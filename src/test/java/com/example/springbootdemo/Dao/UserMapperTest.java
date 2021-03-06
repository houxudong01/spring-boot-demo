package com.example.springbootdemo.Dao;

import com.example.springbootdemo.dao.UserMapper;
import com.example.springbootdemo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() {
        User user = new User("user1", "user1", null);
        int save = this.userMapper.save(user);
        assertEquals(1, save);
    }

    @Test
    public void listAll() {
        List<User> users = this.userMapper.listAll();
        assertTrue(!users.isEmpty());
    }
}
