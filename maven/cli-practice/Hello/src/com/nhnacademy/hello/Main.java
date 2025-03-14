package com.nhnacademy.hello;

/**
 * 외부 라이브러리 jar 활용하기
 */
// vscode의 툴에서 실행할 때는 잘 되지만
// 직접 javac로 컴파일할 때는 해당 라이브러리를 찾을 수 없다.
// 참조하고 있는 라이브러리가 있을 때는 class-path를 지정해야 한다.
//
// 1.lib 폴더에 외부 라이브러리 jar 파일을 넣기
// 2.컴파일 할 때 외부 라이브러리의 클래스패스도 같이 지정해서 out 폴더에 컴파일 : javac ./src/com/nhnacademy/hello/*.java -d ./out/ --class-path ./lib/calculator.jar
// 3.out 폴더로 이동 : cd out
// 4.out 폴더에서 calculator.jar 생성 : javac ./src/com/nhnacademy/hello/*.java -d ./out/ --class-path ./lib/calculator.jar
// 5.out 폴더에서 hello.jar 생성 : jar --file hello.jar --main-class com.nhnacademy.hello.Main -c ./com
// 6.out 폴더에서 hello.jar 실행 : java -cp "./hello.jar;./calculator.jar" com.nhnacademy.hello.

// 해당 라이브러리 클래스의 경로 import
import com.nhnacademy.hello.util.Calculator;

public class Main {
    // 여기서 run 버튼으로 실행하면 settings.json으로 환경이 조성된 vscode 툴이 해주는 것
    public static void main(String[] args) {
        System.out.println("hello");

        int a = 100;
        int b = 10;

        System.out.println("plus:" + Calculator.plus(a,b));
        System.out.println("minus:" + Calculator.subtract(a,b));
        System.out.println("divide:" + Calculator.divide(a,b));
        System.out.println("multifly:" + Calculator.multiply(a,b));
    }

    /**
     * 터미널에서 직접 컴파일 및 실행하는 법
     */
    // 1.새터미널 생성
    // 2.bin디렉토리로 이동 : cd bin
    // 3.파일 확인 : ls
    // 4.bin디렉토리에서의 ./com 폴더 삭제 : rm ./com
    // 5.컴파일 실행 : javac ../src/com/nhnacademy/hello/*.java -d ./out
    //                 주의점 주석 한글이 있으면 인코딩 에러 발생하므로 없어야함 
}
