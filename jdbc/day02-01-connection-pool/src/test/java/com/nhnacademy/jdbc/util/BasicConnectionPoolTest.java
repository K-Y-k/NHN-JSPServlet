package com.nhnacademy.jdbc.util;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class BasicConnectionPoolTest {

    static BasicConnectionPool basicConnectionPool;
    static Connection connection1;
    static Connection connection2;
    static Connection connection3;
    static Connection connection4;
    static Connection connection5;

    @BeforeAll
    static void setUp() {
        // todo#0 - jdbcUrl, username, password를 설정하세요
        basicConnectionPool = new BasicConnectionPool(com.mysql.cj.jdbc.Driver.class.getName(),"jdbc:mysql://s4.java21.net:13306/nhn_academy_104","nhn_academy_104","jJ8xngen!",5);
    }

    @AfterAll
    static void connectionClose() throws SQLException {
        basicConnectionPool.destroy();
    }

    @Test
    @Order(0)
    @DisplayName("Driver not found Exception")
    void init(){
        Assertions.assertThrows(RuntimeException.class,
            ()-> new BasicConnectionPool("org.mariadb.jdbc.Driver","jdbcUrl","userName","password",5)
        );
    }

    @Test
    @Order(1)
    @DisplayName("get connection")
    void getConnection() throws InterruptedException, SQLException {
        connection1 = basicConnectionPool.getConnection();
        connection2 = basicConnectionPool.getConnection();
        connection3 = basicConnectionPool.getConnection();

        Assertions.assertAll(
                ()->Assertions.assertTrue(connection1.isValid(1000)),
                ()->Assertions.assertTrue(connection2.isValid(1000)),
                ()->Assertions.assertTrue(connection3.isValid(1000)),
                ()->Assertions.assertEquals(basicConnectionPool.getUsedConnectionSize(),3)
        );
    }

    @Test
    @Order(2)
    @DisplayName("empty connection-pool")
    void getConnection_empty() throws InterruptedException, SQLException {
        connection4 = basicConnectionPool.getConnection();
        connection5 = basicConnectionPool.getConnection();

        /**
         * getConnection() 메서드로 쓰레드 풀에서 쓰레드를 가져오려 하지만
         * 현재 쓰레드들이 모두 쓰레드 풀에서 꺼내서 사용중이라서 쓰레드 풀을 담은 큐는 비어 있어
         * wait()이 걸려있어 현재 테스트 쓰레드가 무한 대기 상태가 된다.
         */
        // Connection connection6 = basicConnectionPool.getConnection();

        Assertions.assertAll(
                ()->Assertions.assertEquals(basicConnectionPool.getUsedConnectionSize(),5)
        );
    }

    @Test
    @Order(3)
    @DisplayName("release connection")
    void releaseConnection() {
        basicConnectionPool.releaseConnection(connection1);
        basicConnectionPool.releaseConnection(connection2);
        basicConnectionPool.releaseConnection(connection3);
        basicConnectionPool.releaseConnection(connection4);
        basicConnectionPool.releaseConnection(connection5);

        Assertions.assertEquals(basicConnectionPool.getUsedConnectionSize(),0);
    }
}