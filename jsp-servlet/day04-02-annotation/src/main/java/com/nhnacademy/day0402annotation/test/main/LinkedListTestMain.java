package com.nhnacademy.day0402annotation.test.main;

import com.nhnacademy.day0402annotation.test.LinkedListTest;
import com.nhnacademy.day0402annotation.test.proxy.ListTestProxy;

public class LinkedListTestMain {
    public static void main(String[] args) {
        LinkedListTest linkedListTest = new LinkedListTest();
        // 동작은 하지만 실행시간에 대한 측정은 일어나지 않는다.
        linkedListTest.test();

        // LinkedListTest를 ListTestProxy에 통해서 실행
        ListTestProxy listTestProxy = new ListTestProxy(linkedListTest);
        listTestProxy.test();
        /**
         * 시간측정 시작: 1742274156988
         * 시간측정 종료:1742274195058
         * 실행시간(초):38
         */
    }
}