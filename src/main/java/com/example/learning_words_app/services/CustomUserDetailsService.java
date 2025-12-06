package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.UserEntity;
import com.example.learning_words_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + nickname));

        // Преобразуйте вашу сущность в UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getNickname())
                .password(user.getHashPass())
                .build();
    }
}