package com.nhnacademy.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // DriverManager는 자바 응용프로그램을 JDBC 드라이버에 연결시켜 주는 클래스
            // DriverManager의 getConnection 메소드로 Connection을 가져올 수 있다.

            // URL에서 jdbc: 이후에 지정되는 형식은 데이터베이스 제조사마다 다릅니다. 제조사에서 정한 규칙에 따라 등록해야 한다.
            // protocol : jdbc:mysql: , jdbc:mysql:loadbalance: , jdbc:mysql:replication: 등을 제공한다.
            // 여기서는 일반적인 프로토콜인 jdbc:mysql: 을 사용했고 원격 주소 활용
            connection = DriverManager.getConnection("jdbc:mysql://s4.java21.net:13306/nhn_academy_104","nhn_academy_104","jJ8xngen!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}