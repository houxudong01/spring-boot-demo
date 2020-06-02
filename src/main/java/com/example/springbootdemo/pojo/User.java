package com.example.springbootdemo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.Transient;
import java.util.Date;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
public class User {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String token;

    private Date birthday;

    private Date gmtCreate;

    private Date gmtModified;

    public User() {
    }

    public User(String username, String password, Date birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", birthday=" + birthday +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
