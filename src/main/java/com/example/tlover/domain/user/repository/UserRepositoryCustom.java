package com.example.tlover.domain.user.repository;

import java.util.List;

public interface UserRepositoryCustom {

    List<String> findThemaNamesByUserId(Long userId);

    List<String> findRegionByUserId(Long userId);


}
