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
public class AtomicMain {

    /**
     * Atomicity(원자성)
     * - 트랜잭션의 수행결과는 데이터베이스에 전부 반영되거나, 전부 반영되지 않아야 한다. (All or Nothing)
     * - Transaction 수행과정 중 A의 계좌값을 감소했는데 이후에 에러가 발생한다면
     *   A 돈은 사라지고 B는 돈을 받을 수 없게된다.
     * - 이렇게 트랜젝션 작업 중 문제가 생기면 전체 작업을 취소(ROLLBACK) 하는 과정을 거쳐야 한다.
     */
    public static void main(String[] args) throws SQLException {
        BankService bankService = new BankServiceImpl(new AccountRepositoryImpl());

        Connection connection = DbUtils.getDataSource().getConnection();
        // 트랜잭션 단위로 진행하기 위해 자동 commit을 false로 설정
        connection.setAutoCommit(false);
        // 격리수준을 제일 높은 단계(다른 트랜잭션 진행중일 때 접근 못함)로 설정
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        // todo1 - 계좌생성 : 8000
        bankService.createAccount(connection,new Account(8000l,"nhn아카데미-8000",10_0000l ));

        try {
            // todo2 - 8000계좌에서 -> 50000인출
            bankService.withdrawAccount(connection,8000l,5_0000);

            // todo3 - 9000 계좌에 -> 50000입급
            /**
             * 9000 계좌는 존재하지 않음으로 예외 발생
             */
            bankService.depositAccount(connection,9000l,5_0000);

            connection.commit();
        } catch (Exception e){
            log.debug("withdraw:{}",e.getMessage());
            
            // todo4 - rollback 처리
            connection.rollback();
        }

        connection.close();
    }
}