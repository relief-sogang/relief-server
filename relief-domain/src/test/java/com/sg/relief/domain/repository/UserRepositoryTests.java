package com.sg.relief.domain.repository;


import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userDeleteTest() {
        //List<User> userList =  userRepository.findAll();
        //System.out.println("userList = " + userList);
        Optional<User> ayay = userRepository.findByUserId("Ayay");
        Optional<User> ahyoung = userRepository.findByUserId("ahyoung");
        ayay.ifPresent(u -> {
            userRepository.delete(u);
        });
        ahyoung.ifPresent(u -> {
            userRepository.delete(u);
        });
    }
}
