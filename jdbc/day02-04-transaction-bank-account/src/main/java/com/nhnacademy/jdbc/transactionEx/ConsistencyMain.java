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
public class ConsistencyMain {
    static BankService bankService = new BankServiceImpl(new AccountRepositoryImpl());

    /**
     * Consistency(일관성)
     * - 트랜잭션 수행 후 데이터 모델의 모든 제약조건을 만족해야 한다.
     * - 해당 컬럼이나 레코드에 명시된 제약조건,
     * - ex1) 잔고의 데이터 타입은 정수형이어야 한다,
     *   ex2) 잔고의 값은 null 이면 안된다,
     *   ex3) 잔고가 늘어나면 신용평가 값도 변경되어야 한다 등
     *   기본 키와 외래키, 속성에 대한 제약조건과 같은 명시적 무결성 제약 조건 을 만족시켜야 한다.
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = DbUtils.getDataSource().getConnection();
        connection.setAutoCommit(false);
        init(connection);

        try {
            Account account1 = new Account(8000, "nhn아카데미", 10_0000);

            // todo#1 계좌번호가 8000인 account1 생성
            bankService.createAccount(connection, account1);
            log.debug("account1->8000계좌 생성");

            // todo#2 계좌번호가 8000인 account2생성
            /**
             * account1,8000 계좌가 생성되어 있어 예외 발생
             */
            Account account2 = new Account(8000, "nhn아카데미", 10_0000);
            bankService.createAccount(connection, account2);
            log.debug("account2->8000계좌 생성 시도");

            connection.commit();

        } catch (Exception e) {
            log.debug("error:{}", e.getMessage());
            log.debug("account1,8000 계좌가 생성되어 있음으로 rollback 처리, account1, account2 동일한 Transaction에 속함으로 모두 rollback");

            // todo#3 account_number(계좌번호) primary key 제약조건 위배. -> rollback 처리
            connection.rollback();
        }

        connection.close();
    }

    public static void init(Connection connection){
        // 8000계좌가 있다면 미리 삭제함.
        try {
            if (bankService.isExistAccount(connection,8000l)) {
                bankService.dropAccount(connection, 8000l);
            }
        } catch (Exception e) {
            log.debug("init:{}",e.getMessage());
        }
    }
}