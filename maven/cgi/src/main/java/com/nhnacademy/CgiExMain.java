package com.nhnacademy;

public class CgiExMain {
    public static void main(String[] args) {
        // 전달받은 파라미터 할당
        // 실행쪽에 Edit Configuration에서 args 배열에 담을 파라미터 인자 설정 가능
        String SERVER_NAME = args[0];
        String PORT = args[1];
        String REMOTE_ADDR = args[2];

        System.out.println("Server Name:" + SERVER_NAME + ", Port:" + PORT + ", RemoteName:" + REMOTE_ADDR);

        String message = String.format("<html>"
                + "<body>"
                +   "<h1>Hello World!</h1>"
                +   "<h2>%s</h2>"
                +   "<h2>%s</h2>"
                +   "<h2>%s</h2>"
                + "</body>"
                + "</html>", SERVER_NAME, PORT, REMOTE_ADDR);

        System.out.println(message);
    }
}
