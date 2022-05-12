package com.example.tlover.domain.rating.repository;

import com.example.tlover.domain.rating.entity.Rating;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUser(User user);
}
