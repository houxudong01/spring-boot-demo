package com.example.springbootdemo.web;

import com.example.springbootdemo.pojo.ApiResult;
import com.example.springbootdemo.pojo.User;
import com.example.springbootdemo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ApiResult<User> register(@RequestParam String username, @RequestParam String password, Date birthday) throws JsonProcessingException {
        User user = this.userService.saveUser(username, password, birthday);
        return new ApiResult<>(user);
    }

    @GetMapping("/getUser")
    public ApiResult<User> getUser(@RequestParam Long id) throws IOException {
        return new ApiResult<>(this.userService.get(id));
    }

    @GetMapping("/listAll")
    public ApiResult<List<User>> listAll() {
        List<User> users = this.userService.listAll();
        return new ApiResult<>(users);
    }
}
