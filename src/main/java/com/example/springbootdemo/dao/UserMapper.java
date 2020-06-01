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
public interface UserMapper {

    /**
     * 保存 user
     *
     * @param user
     * @return
     */
    @Insert("insert into user (username, password, birthday) values(#{username}, #{password}, #{birthday})")
    @Options(useGeneratedKeys = true)
    int save(User user);

    /**
     * 根据根据用户 id 获取用心详情
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    /**
     * 根据用户名获取用户详情
     *
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 列出全部用户
     *
     * @return
     */
    @Select("select * from user")
    List<User> listAll();
}
