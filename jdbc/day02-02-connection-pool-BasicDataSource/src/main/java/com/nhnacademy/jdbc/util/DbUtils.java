package com.nhnacademy.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        /**
         * BasicDataSource는 Apache Commons DBCP (Database Connection Pooling) 라이브러리에서 제공하는 데이터베이스 연결 풀 객체
         * BasicDataSource 클래스의 setter 메서드로 커넥션 풀을 설정할 수 있다.
         */
        BasicDataSource basicDataSource = new BasicDataSource();

        // mysql 전용 드라이버로 설정
        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#0 {ip},{databases},{username},{password}설정
        basicDataSource.setUrl("jdbc:mysql://s4.java21.net:13306/nhn_academy_104");
        basicDataSource.setUsername("nhn_academy_104");
        basicDataSource.setPassword("jJ8xngen!");

        // 최초 connection pool 시작될 때 초기 Connection 개수
        basicDataSource.setInitialSize(5);
        // 최대로 사용할 수 있는 Connection 개수
        basicDataSource.setMaxTotal(5);
        // Connection pool에 반납할 때 최대 유지될 수 있는 Connection 수
        basicDataSource.setMaxIdle(5);
        // 최소한으로 유지될 Connection 수
        basicDataSource.setMinIdle(5);

        // 풀이 예외를 발생시키기 전에 연결이 반환될 때까지 대기하는 시간(밀리초 단위)
        basicDataSource.setMaxWait(Duration.ofSeconds(2));

        // Connection pool에 Connection을 반환하기 전에 해당 풀의 연결여부를 확인
        // mysql : select 1
        // oracle : select 1 from dual
        basicDataSource.setValidationQuery("select 1");

        // pool에서 Connection을 사용하기 위해서(Connction pool에서 Connection 얻어올 때) 유효성 검사 여부
        basicDataSource.setTestOnBorrow(true);

        // 설정한 BasicDataSource를 DATASOURCE에 주입
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }
}