package com.example.springbootdemo.dao;

import com.example.springbootdemo.pojo.User;
import org.apache.ibatis.annotations.*;
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
    @Insert("insert into user (username, password, token) values(#{username}, #{password}, #{token})")
    @Options(useGeneratedKeys = true)
    int save(User user);

    /**
     * 根据根据用户 id 获取用心详情
     *
     * @param id
     * @return
     */
    @Select("select id, username, password, token from user where id = #{id}")
    User getById(Long id);

    /**
     * 根据用户名获取用户详情
     *
     * @param username
     * @return
     */
    @Select("select id, username, password, token from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 根据 token 获取用户详情
     *
     * @param token
     * @return
     */
    @Select("select id, username, password, token from user where token = #{token}")
    User getByToken(String token);

    /**
     * 更新用户 token
     *
     * @param id
     * @param token
     * @return
     */
    @Update("update user set token = #{token} where id = #{id}")
    int updateUserToken(@Param("id") Long id, @Param("token") String token);

    /**
     * 列出全部用户
     *
     * @return
     */
    @Select("select id, username, password from user")
    List<User> listAll();
}
