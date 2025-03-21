package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        String sql = "SELECT * FROM jdbc_user WHERE user_id='"+ userId + "'and user_password='" + userPassword +"'";

        try (Statement stmt = DbUtils.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String user_id = rs.getString(1);
                String user_name = rs.getString(2);
                String user_password = rs.getString(3);

                User user = new User(user_id, user_name, user_password);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        String sql = "SELECT * FROM jdbc_user WHERE user_id = '" + userId + "'";

        try (Statement stmt = DbUtils.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String user_id = rs.getString("user_id");  // 컬럼 이름 사용
                String user_name = rs.getString("user_name");
                String user_password = rs.getString("user_password");

                User findUser = new User(user_id, user_name, user_password);
                return Optional.of(findUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        String sql = "INSERT INTO jdbc_user VALUES ('" +
                user.getUserId() + "', '" + user.getUserName() + "', '" + user.getUserPassword() +  "')";

        try (Statement stmt = DbUtils.getConnection().createStatement()) {
            int rs = stmt.executeUpdate(sql);

            if (rs > 0) {
                log.info("데이터 삽입 성공");
            }

            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        String sql = "UPDATE jdbc_user SET user_password = '" + userPassword + "' WHERE user_id = '" + userId + "'";

        try (Statement stmt = DbUtils.getConnection().createStatement()) {
            int rs = stmt.executeUpdate(sql);
            return rs;
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        String sql = "DELETE FROM jdbc_user WHERE user_id = '" + userId + "'";

        try (Statement stmt = DbUtils.getConnection().createStatement()) {
            int rs = stmt.executeUpdate(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
