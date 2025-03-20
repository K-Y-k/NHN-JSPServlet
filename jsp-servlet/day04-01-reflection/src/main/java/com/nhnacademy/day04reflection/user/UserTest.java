package com.nhnacademy.day04reflection.user;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserTest {
    public static void main(String[] args) {
        /**
         * reflection으로 객체 생성법
         */
        try {
            // 물리적인 클래스 파일명을 인자로 넘겨주면 이에 해당하는 클래스를 반환
            Class userClass = Class.forName(User.class.getName());

            // userClass.getConstructor() : public 접근자를 가진 생성자를 반환
            Constructor<?> constructor = userClass.getConstructor();

            // reflection으로 생성한 User 객체
            User user = (User) constructor.newInstance();
            user.setUserName("kyk");
            user.setUserAge(28);

            System.out.println(user);
            System.out.println();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /**
         * reflection으로 객체의 메소드 활용법
         */
        try {
            Class clazz = Class.forName(User.class.getName());
            Object user = clazz.getDeclaredConstructor().newInstance();

            // 리플랙션 api를 이용한 메소드 호출
            // setUserName()
            Method setUserNameMethod =clazz.getDeclaredMethod("setUserName", String.class);
            setUserNameMethod.invoke(user, "NHN 아카데미");

            // getUserName()
            Method getUserNameMethod =clazz.getDeclaredMethod("getUserName");
            String userName = (String) getUserNameMethod.invoke(user);

            // setUserAge()
            Method setUserAgeMethod =clazz.getDeclaredMethod("setUserAge",Integer.TYPE);
            setUserAgeMethod.invoke(user, 30);

            // getUserAge()
            Method getUserAgeMethod =clazz.getDeclaredMethod("getUserAge");
            int userAge = (int) getUserAgeMethod.invoke(user);

            System.out.println("userName:" + userName);
            System.out.println("userAge:" + userAge);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
