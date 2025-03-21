package com.nhnacademy.jdbc.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
class DbUtilsTest {
    private static Connection connection = null;

    @BeforeAll
    static void setUp(){
        log.info("connection open()");
        connection = DbUtils.getConnection();
    }

    @Test
    @DisplayName("connected")
    void connectionTest(){
        Assertions.assertNotNull(connection);
    }

    @Test
    @DisplayName("mysql driver load : success")
    void mtsql_driverLoadTest(){
        // 자바 리플렉션
        try {
            // JDBC 드라이버를 로딩
            // new com.mysql.cj.jdbc.Driver() 로 직접 초기화하지 않는 것은 응용시스템과 Driver 구현체가 강 결합하지 않도록 하기 위함
            // Driver 로드는 전체 프로젝트에서 한 번만 실행해야 한다.
            Class<?> driver = Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("driver:{}", driver.getName());

            Assertions.assertEquals(driver.getName(),com.mysql.cj.jdbc.Driver.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("mariadb driver load :fail")
    void mariadb_driverLoadTest(){
        Assertions.assertThrows(ClassNotFoundException.class,()->{
            Class.forName("org.mariadb.jdbc.Driver");
        });
    }

    @AfterAll
    static void release() {
        log.info("connection close()");
        try {
            // Connection 객체를 더 이상 사용하지 않을 때 Connection.close() 메소드를 호출한다.
            // 시스템에서 Connection 을 close() 하지 않으면 메모리 누수가 발생하여 시스템 장애가 발생한다.
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}