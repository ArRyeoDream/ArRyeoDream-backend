package com.kimbab.ArRyeoDream.user.repository;

import com.kimbab.ArRyeoDream.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByOauth2Id(String oauth2Id);
    Boolean existsByOauth2Id(String oauth2Id);
}
