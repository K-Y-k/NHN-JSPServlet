package com.nhnacademy.day04reflection.di;

import com.nhnacademy.day04reflection.user.User;

public class DiTest {
    public static void main(String[] args) {
        // InjectUtil에서 받아온 객체인 UserService 사용
        UserService userService = InjectUtil.getObject(UserService.class);

        // 샘플 user 생성
        User user = new User("marco1",10);

        // user 추가
        userService.addUser(user);
        
        // 저장한 userName이 ‘marco1’인 user 객체를 조회
        System.out.println(userService.getUser("marco1"));
    }
}
