package com.example.tlover.global.init.factory;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.global.encryption.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFactory {

    private final SHA256Util sha256Util;

    public List<User> createUserLists() {
        User user0 = User.builder()
                .userLoginId("codingbom")
                .userNickName("코딩범")
                .userPassword(sha256Util.encrypt("test123!!"))
                .userEmail("coding@gmail.com")
                .userState("activate")
                .userPhoneNum("01052435980")
                .build();
        User user1 = User.builder()
                .userLoginId("seongsik")
                .userNickName("윤성식")
                .userPassword(sha256Util.encrypt("test123!!"))
                .userEmail("seongsik@gmail.com")
                .userState("activate")
                .userPhoneNum("01052435980")
                .build();
        User user2 = User.builder()
                .userLoginId("test123")
                .userNickName("여치여치")
                .userPassword(sha256Util.encrypt("test123!!"))
                .userEmail("test123@gmail.com")
                .userState("activate")
                .userPhoneNum("01052435980")
                .build();
        return List.of(user0, user1, user2);
    }
}
