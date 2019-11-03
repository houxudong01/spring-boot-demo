package com.example.springbootdemo.Dao;

import com.example.springbootdemo.dao.UserDao;
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
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        User user = new User("user1", "user1", null);
        int save = this.userDao.save(user);
        assertEquals(1, save);
    }

    @Test
    public void listAll() {
        List<User> users = this.userDao.listAll();
        assertTrue(!users.isEmpty());
    }
}
