package com.nhnacademy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.*;
import java.util.Random;

public class Lab01And02 {
    public static void main( String[] args ) {
        // 실습1 : 1 ~ 100까지 출력
        Random random = new Random();
        int rand1 = random.nextInt(100) + 1;
        System.out.println("Random 클래스의 1 ~ 100 랜덤 값 : " + rand1);

        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int rand2 = randomDataGenerator.nextInt(1, 100);
        System.out.println("RandomDataGenerator 클래스의 1 ~ 100 랜덤 값 : " + rand2);


        // 실습2 : 문자열의 Null 또는 빈 문자열 체크
        String nullStr = null;
        String emptyStr = "";
        System.out.println("Objects.isNull():" + Objects.isNull(nullStr) + ", java.lang.String.isEmpty():" + emptyStr.isEmpty());
        System.out.println("java.lang.String.isEmpty() : " + StringUtils.isEmpty(nullStr));
    }
}
