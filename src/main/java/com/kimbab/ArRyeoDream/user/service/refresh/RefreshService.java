package com.kimbab.ArRyeoDream.user.service.refresh;

import com.kimbab.ArRyeoDream.user.dto.JwtTokenDTO;

public interface RefreshService {
    void storeRefresh(JwtTokenDTO jwtTokenDTO, String oauth2Id);
    String getRefresh(String refreshToken);
    void deleteRefresh(String refreshToken);
}
