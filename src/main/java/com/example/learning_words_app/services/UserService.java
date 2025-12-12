package com.example.learning_words_app.services;

import com.example.learning_words_app.entities.Role;
import com.example.learning_words_app.entities.TrainingEntity;
import com.example.learning_words_app.entities.UserEntity;
import com.example.learning_words_app.repositories.UserRepository;
import com.example.learning_words_app.dto.RegistrationForm;
import com.example.learning_words_app.dto.ProfileInfoViewModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity getEntityByNickname(String nick) {
        return userRepository.findByNickname(nick).orElseThrow(
                () -> new EntityNotFoundException("Not found user with nickname = " + nick)
        );
    }


    public ProfileInfoViewModel getInfoByName(String name) {
        UserEntity userEntity = userRepository.findByNickname(name).orElseThrow(
                () -> new EntityNotFoundException("Not found user with nickname = " + name)
        );
        Duration duration = Duration.ZERO;
        for (TrainingEntity training : userEntity.getTrainings()) {
            if (training.getEndDate() == null) {
               continue;
            }
            duration = duration.plus(Duration.between(training.getCreateDate(), training.getEndDate()));
        }
        String timeTrainings;
        if (duration.toHours() >= 1) {
            timeTrainings = String.format("%d часов %d минут", duration.toHoursPart(), duration.toMinutesPart());
        }
        else {
            timeTrainings = String.format("%d минут %d секунд", duration.toMinutesPart(), duration.toSecondsPart());
        }
        return new ProfileInfoViewModel(userEntity.getId(),
                                        userEntity.getNickname(),
                                        timeTrainings,
                                        userEntity.getTrainings().size(),
                                        userEntity.getTimeRegistration());
    }


    public void createAccount(RegistrationForm form) {
        UserEntity userEntity = new UserEntity(form.nickname(),
                                               passwordEncoder.encode(form.password()),
                                               Role.USER, // создаём пользователя при регистрации
                                               LocalDateTime.now());
        userRepository.save(userEntity);
        System.out.println("Зарегистрировался: " + userEntity);
    }


    public boolean isAdmin(String name) {
        UserEntity userEntity = getEntityByNickname(name);
        return userEntity.getRole().equals(Role.ADMIN);
    }
}

