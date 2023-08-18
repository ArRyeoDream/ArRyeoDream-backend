package com.kimbab.ArRyeoDream.user.service;

import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User findByOauth2Id(String oauth2Id){
        return userRepository.findByOauth2Id(oauth2Id).get();
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }
}
