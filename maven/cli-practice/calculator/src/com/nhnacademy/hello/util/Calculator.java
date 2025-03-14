package com.nhnacademy.hello.util;

public final class Calculator {
    /**
     * 현재 이 클래스는 main()이 없는 클래스다.
     * 즉, 이러한 기능을 구현한 클래스들이 라이브러리고고 
     * 라이브러리를 jar로 만들어보기
     */
    // 1.컴파일 : javac ./src/com/nhnacademy/hello/util/*.java -d ./out
    // 2.out 폴더로 이동 : cd out
    // 3.out 폴더에서 jar 파일로 압축 : jar --file calculator.jar -c ./com
    private Calculator() {
        throw new IllegalStateException("Utility Class!");
    }

    public static int plus(int a, int b) {
        return a + b;
    }
    
    public static int subtract(int a, int b){
        return a - b;
    }

    public static int multiply(int a, int b){
        return a * b;
    }

    public static int divide(int a, int b){
        return a / b;
    }
}
