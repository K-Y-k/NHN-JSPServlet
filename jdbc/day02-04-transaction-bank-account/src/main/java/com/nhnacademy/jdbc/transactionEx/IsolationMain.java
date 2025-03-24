package com.nhnacademy.jdbc.transactionEx;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.impl.AccountRepositoryImpl;
import com.nhnacademy.jdbc.bank.service.BankService;
import com.nhnacademy.jdbc.bank.service.impl.BankServiceImpl;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class IsolationMain {
    static final BankService bankService = new BankServiceImpl(new AccountRepositoryImpl());

    /**
     * Isolation(고립성)
     * - 트랜잭션 수행 시 다른 트랜잭션이 영향을 미치지 않아야 한다.
     *
     * - ex)
     *   A의 잔고가 100,000원이 있다고 가정하자.
     *   A는 B에게 10,000원을 송금하는 동시에 100,000원을 인출하려 한다고 가정한다.
     *
     *   이 경우 송금 트랜잭션과 인출 트랜잭션이 동시에 수행된다.
     *
     *   A가 B에게 송금할 경우 A의 잔고에서 10,000원이 차감된다. 
     *   따라서 A의 잔고는 90,000원이 되어 100,000원을 인출하지 못해야 정상이다.
     *
     *   그런데 만약 송금 트랜잭션 중간에 인출 트랜잭션이 끼어들게 되면 어떻게 될까? 
     *   송금 트랜잭션이 디스크에서 100,000원을 메모리에 적재해 90,000원으로 값을 변경하여
     *   다시 DB에 저장하기 전에, 인출 트랜잭션이 A의 잔고에 접근해 작업을 마무리한다고 가정해보자.
     *
     *   이렇게 되면 송금 트랜잭션이 뒤늦게 DB에 90,000원을 저장하기 때문에
     *   송금과 인출을 마치고도 A의 잔고에는 90,000원이 들어있을 것이다.
     */
    public static void main(String[] args) throws SQLException, InterruptedException {
        // todo#1 준비 Account (A,B) 잔고 : 각 10만원
        init();

        Thread.sleep(1000);

        // todo#2 A->B에게 만(원) 송금
        Thread threadA = transferThread();
        threadA.setName("송금-Thread");

        // todo#3 A가 10만(원) 인출 시도
        /**
         * A는 송금 후 잔고는 9만원이므로 -> 송금실패 -> 예외발생
         */
        Thread threadB = withdrawThread();
        threadB.setName("인출-Thread");

        threadA.start();
        // todo#6 - Thread.sleep(1000)  주석을 걸면 어떻게 될까요?
        Thread.sleep(1000);
        threadB.start();

        // todo#4 threadA, threadB 모두 실행될 때까지 Main Thread 대기.
        while (!(threadA.getState().equals(Thread.State.TERMINATED) && threadB.getState().equals(Thread.State.TERMINATED))) {
            Thread.yield();
        }

        // todo#5 조회
        Connection connection = DbUtils.getDataSource().getConnection();
        Account accountA = bankService.getAccount(connection, 10000);
        Account accountB = bankService.getAccount(connection, 20000);

        log.debug("=================================");
        log.debug("accountA:{}", accountA);
        log.debug("accountB:{}", accountB);
        log.debug("=================================");

    }

    public static void init() throws SQLException {
        Connection connection = DbUtils.getDataSource().getConnection();
        connection.setAutoCommit(false);

        // todo#1 account (A,B) 잔고 : 10_0000 생성합니다.
        Account accountA = new Account(10000,"nhn아카데미-10000",10_0000l);
        Account accountB = new Account(20000,"nhn아카데미-20000",10_0000l);

        try {
            if (bankService.isExistAccount(connection,10000l)) {
                bankService.dropAccount(connection,10000l);
            }
            if (bankService.isExistAccount(connection,20000l)) {
                bankService.dropAccount(connection,20000l);
            }

            bankService.createAccount(connection,accountA);
            bankService.createAccount(connection,accountB);
            connection.commit();
        } catch (Exception e) {
            log.info("error:{}",e);
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public static Thread transferThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = null;

                try {
                    connection = DbUtils.getDataSource().getConnection();
                    connection.setAutoCommit(false);

                    Thread.sleep(1000);

                    // 10000 계좌에서 20000 계좌로 1만원 송금
                    bankService.transferAmount(connection,10000,20000,1_0000);

                    connection.commit();
                    log.debug("송금완료!");
                } catch (Exception e) {
                    log.debug("송금error:{}",e);

                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    throw new RuntimeException(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        
        return thread;
    }

    public static Thread withdrawThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = null;
                try {
                    connection = DbUtils.getDataSource().getConnection();
                    connection.setAutoCommit(false);
                    
                    // 송금이 먼저 실행될 수 있도록 sleep 1000
                    Thread.sleep(1000);
                    
                    // 10000 계좌에서 10만원 출금
                    bankService.withdrawAccount(connection,10000,10_0000);
                    connection.commit();
                    log.debug("A-> 10만원 인출 완료");
                } catch (Exception e) {
                    log.debug("출금 error:{}",e.getMessage());
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    throw new RuntimeException(e);
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        return thread;
    }
}
