package com.nhnacademy.day0402annotation.test;

import com.nhnacademy.day0402annotation.annotation.StopWatch;

import java.util.LinkedList;
import java.util.List;

public class LinkedListTest implements PerformanceTestable {

    // 실행시간 측정을 자동화할 StopWatch 어노테이션 적용
    @StopWatch
    @Override
    public void test() {
        List<Integer> integerList = new LinkedList<>();

        for (int i = 0; i < 100000000; i++) {
            integerList.add(i);
        }
    }
}
