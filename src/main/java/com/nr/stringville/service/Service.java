package com.nr.stringville.service;

import com.nr.stringville.dto.User;

import java.util.List;

public interface Service {
    List<User> getAllUsers();
    User updateUser(String name, String str);
    void reset();
    String getResult();
    String getHealth();
    int getScore(String str);
}
