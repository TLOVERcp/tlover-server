package com.example.tlover.domain.user_region.repository;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_region.entity.UserRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRegionRepository extends JpaRepository<UserRegion , Long> {
    List<UserRegion> findByUser_UserId(Long userId);

    @Transactional
    void deleteAllByUser(User user);
}
