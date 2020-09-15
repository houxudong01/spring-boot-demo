package com.example.springbootdemo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.Date;

/**
 * @author HouXudong
 * @data 2018-12-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String token;

    private Date birthday;

    private Date gmtCreate;

    private Date gmtModified;

    public User(String username, String password, Date birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

}
