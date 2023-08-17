package com.kimbab.ArRyeoDream.common.jwt.user;

import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user = userRepository.findByOauth2Id(username);
        if(user.isPresent()){
            return user.get();
        }
        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
}
