package com.example.tlover.domain.user.service;

import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.exception.NotFoundThemaNameException;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.entity.UserThema;
import com.example.tlover.domain.user.repository.UserThemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserThemaServiceImpl implements UserThemaService {

    private final EntityManager em;
    private final UserThemaRepository userThemaRepository;
    private final ThemaRepository themaRepository;

    /**
     * 유저 테마 이름 조회
     * @param userId
     * @return User
     * @author 윤여찬
     */
    @Override
    public List<String> getUserThemaName(Long userId) {
        List<String> userThemaNames = new ArrayList<String>();
        List<UserThema> userThemas = userThemaRepository.findByUserUserId(userId);

        if (!userThemas.isEmpty()) {

            for (UserThema userThema : userThemas) {
                Thema thema = themaRepository.findByThemaId(userThema.getThema().getThemaId()).get();
                userThemaNames.add(thema.getThemaName());
            }
        }
        return userThemaNames;
    }

    /**
     * 유저 테마 등록
     * @param themaNameList, user
     * @return User
     * @author 윤여찬
     */
    @Override
    @Transactional
    public void insertUserThema(List<String> themaNameList, User user) {

        if (!themaNameList.isEmpty()) {

            for (String themaName : themaNameList) {
                UserThema userThema = new UserThema();
                userThema.setUser(user);
                userThema.setThema(themaRepository.findByThemaName(themaName));
                em.persist(userThema);
            }
        }

    }

    /**
     * 유저 테마 변경
     * @param themaNameList, user
     * @return
     * @author 윤여찬
     */
    @Override
    @Transactional
    public void updateUserThema(List<String> themaNameList, User user) {

        List<UserThema> userThemaList = userThemaRepository.findByUserUserId(user.getUserId());

        for (int i = 0; i < userThemaList.size(); i++) {
            userThemaRepository.delete(userThemaList.get(i));
        }

        if (!themaNameList.isEmpty()) this.insertUserThema(themaNameList, user);


    }

    /**
     * 테마 이름 확인
     * @param themaNameList
     * @return
     * @author 윤여찬
     */
    public void checkThemaName(List<String> themaNameList) {

        if (!themaNameList.isEmpty()) {

            for (String themaName : themaNameList) {
                Thema thema = themaRepository.findByThemaName(themaName);

                if (thema == null) throw new NotFoundThemaNameException();
            }
        }
    }
}
