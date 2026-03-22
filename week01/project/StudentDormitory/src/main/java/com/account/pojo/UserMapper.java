package com.account.pojo;

import com.account.pojo.User;

import java.util.List;

public interface UserMapper {
    User selectByAccount(String account);
    int selectcompetence(String account);
    List<User> selectAll();
    void insertUser();
}
