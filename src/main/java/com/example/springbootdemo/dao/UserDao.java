package com.example.springbootdemo.dao;

import com.example.springbootdemo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@Mapper
@Repository
public interface UserDao {

    @Insert("insert into user (username, password, birthday) values(#{username}, #{password}, #{birthday})")
    @Options(useGeneratedKeys = true)
    int save(User user);

    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select * from user")
    List<User> listAll();
}
