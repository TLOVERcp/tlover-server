package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.entity.User;

import java.util.List;

public interface UserThemaService {

    List<String> getUserThemaName(Long userId);
    void insertUserThema(List<String> themaNameList, User user);
    void updateUserThema(List<String> themaNameList, User user);
    void checkThemaName(List<String> themaNameList);
}
