package com.kimbab.ArRyeoDream.user.service.refresh;

import com.kimbab.ArRyeoDream.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByOauth2Id(String oauth2Id);
}
