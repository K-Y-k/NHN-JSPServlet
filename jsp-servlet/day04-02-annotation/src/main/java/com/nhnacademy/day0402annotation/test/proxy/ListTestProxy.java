package com.nhnacademy.day0402annotation.test.proxy;

import com.nhnacademy.day0402annotation.test.PerformanceTestable;
import com.nhnacademy.day0402annotation.annotation.StopWatch;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * PerformanceTestable 구현한 ListTestProxy를 생성
 * 즉 ArrayListTest/LinkedListTest는 ListTestProxy로 한 번 감싸서 실행을 한다.
 * 사용자 입장에서는 내부적으로 어떻게 호출 되는지 알 수 없다.
 */
public class ListTestProxy implements PerformanceTestable {
    private final PerformanceTestable performanceTestable;

    public ListTestProxy(PerformanceTestable performanceTestable) {
        this.performanceTestable = performanceTestable;
    }

    @Override
    public void test() {
        if (hasStopWatch()) {
            long start = System.currentTimeMillis();
            System.out.println("##시간측정 시작: " + start );
            performanceTestable.test();

            long end = System.currentTimeMillis();
            System.out.println("##시간측정 종료:" + end);

            long result = (end - start)/1000;
            System.out.println("실행시간(초):" + result);
        }
    }

    private boolean hasStopWatch(){
        for (Method method : performanceTestable.getClass().getDeclaredMethods()) {
            StopWatch stopWatch = method.getAnnotation(StopWatch.class);

            if (Objects.nonNull(stopWatch)){
                return true;
            }
        }

        return false;
    }
}
