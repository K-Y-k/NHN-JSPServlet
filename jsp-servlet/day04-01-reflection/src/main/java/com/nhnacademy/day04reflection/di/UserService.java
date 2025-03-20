package com.nhnacademy.day04reflection.di;

import com.nhnacademy.day04reflection.user.User;

public class UserService {
    /**
     * UserService는
     * UserRepository를 주입받고 UserRepository로부터 user를 조회하고 추가하는 method를 제공
     */
    @Autowired
    private UserRepository userRepository;

    public User getUser(String userName){
        return userRepository.findByName(userName);
    }

    public void addUser(User user){
        userRepository.save(user);
    }
}
