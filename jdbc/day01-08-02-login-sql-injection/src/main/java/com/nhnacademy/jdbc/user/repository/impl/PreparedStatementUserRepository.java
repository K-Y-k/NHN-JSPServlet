package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {
    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#11 -PreparedStatement- 아이디 , 비밀번호가 일치하는 회원조회
        String sql = "SELECT * FROM jdbc_user WHERE user_id = ? and user_password = ?";

        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);

            /**
             * ResultSet도 자원을 해제해야 하므로 try-with-resource절을 썼는데
             * 위에 사용하지 못하는 이유는 PreparedStatement의 set 설정이 아직 안되었기에
             * 내부 try-with-resource절을 사용한 것
             */
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    /**
                     * 메소드 파라미터에 정보가 있지만 해당 위치를 모르는 사람들에게는 가독성이 떨어지므로
                     * db에서 가공할 때 다시 선언함
                     */
                    String user_id = rs.getString(1);
                    String user_name = rs.getString(2);
                    String user_password = rs.getString(3);

                    User user = new User(user_id, user_name, user_password);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#12-PreparedStatement-회원조회
        String sql = "SELECT * FROM jdbc_user WHERE user_id = ?";

        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String user_id = rs.getString(1);
                    String user_name = rs.getString(2);
                    String user_password = rs.getString(3);

                    User user = new User(user_id, user_name, user_password);
                    return Optional.of(user);
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#13-PreparedStatement-회원저장
        String sql = "INSERT INTO jdbc_user(user_id, user_name, user_password) values (?, ?, ?)";

        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getUserPassword());

            return pstmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#14-PreparedStatement-회원정보 수정
        String sql = "UPDATE jdbc_user SET user_password = ? WHERE user_id = ?";

        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, userPassword);
            pstmt.setString(2, userId);

            return pstmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#15-PreparedStatement-회원삭제
        String sql = "DELETE FROM jdbc_user WHERE user_id = ?";

        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, userId);

            return pstmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
