package com.kimbab.ArRyeoDream.user.service.refresh;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;
import com.kimbab.ArRyeoDream.user.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshServiceImpl implements RefreshService{
    private final RefreshTokenRepository refreshRepository;
    @Value("${jwt.refresh-token-expiry}")
    private Long refreshTokenExpiry = 0L;

    @Override
    @Transactional
    public void storeRefresh(JwtTokenDTO jwtTokenDTO, String oauth2Id){
        refreshRepository.deleteByOauth2Id(oauth2Id);
        refreshRepository.save(
                RefreshToken.builder()
                        .refreshToken(jwtTokenDTO.getRefreshToken())
                        .oauth2Id(oauth2Id)
                        .build()
        );
    }

    @Override
    @Transactional
    public String getRefresh(String refreshToken){
        Optional<RefreshToken> findRefreshToken =  refreshRepository.findByRefreshToken(refreshToken);
        if(findRefreshToken.isPresent()){
            if(!isValidate(findRefreshToken.get())){
                refreshRepository.delete(findRefreshToken.get());
                return null;
            }
            return findRefreshToken.get().getOauth2Id();
        }
        else{
            return null;
        }
    }

    @Override
    public void deleteRefresh(String refreshToken){
        Optional<RefreshToken> findRefreshToken = refreshRepository.findByRefreshToken(refreshToken);
        if(findRefreshToken.isPresent()){
            refreshRepository.delete(findRefreshToken.get());
        }
        throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    private Boolean isValidate(RefreshToken refreshToken){
        LocalDateTime expiryDateTime = refreshToken.getCreatedAt().plus(refreshTokenExpiry, ChronoUnit.MILLIS);
        if(LocalDateTime.now().isAfter(expiryDateTime)){
            return false;
        }
        return true;
    }
}
