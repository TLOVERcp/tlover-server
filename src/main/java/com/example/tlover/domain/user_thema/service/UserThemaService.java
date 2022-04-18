package com.example.tlover.domain.user_thema.service;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_thema.entitiy.UserThema;

import java.util.List;

public interface UserThemaService {

    List<String> getUserThemaName(Long userId);
    void insertUserThema(List<String> themaNameList, User user);
    void updateUserThema(List<String> themaNameList, User user);
    void checkUserThema(List<String> themaNameList);
}
