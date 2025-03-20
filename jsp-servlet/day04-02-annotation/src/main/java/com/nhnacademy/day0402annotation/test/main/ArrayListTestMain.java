package com.nhnacademy.day0402annotation.test.main;

import com.nhnacademy.day0402annotation.test.ArrayListTest;
import com.nhnacademy.day0402annotation.test.proxy.ListTestProxy;


public class ArrayListTestMain {
    public static void main(String[] args) {
        ArrayListTest arrayListTest = new ArrayListTest();
        // 동작은 하지만 실행시간에 대한 측정은 일어나지 않는다.
        arrayListTest.test();

        // ArrayListTest를 ListTestProxy에 통해서 실행
        ListTestProxy listTestProxy = new ListTestProxy(arrayListTest);
        listTestProxy.test();
        /**
         * 시간측정 시작: 1742274242797
         * 시간측정 종료:1742274245506
         * 실행시간(초):2
         */
    }
}