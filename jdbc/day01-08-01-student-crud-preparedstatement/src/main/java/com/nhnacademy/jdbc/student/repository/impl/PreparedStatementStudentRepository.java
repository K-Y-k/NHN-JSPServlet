package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 학생 등록
        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement("INSERT INTO jdbc_student VALUES(?, ?, ?, ?, ?)")) {
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, String.valueOf(student.getGender()));
            pstmt.setInt(4, student.getAge());

            // LocalDateTime을 Date로 변환 과정
            // LocalDateTime -> Timestamp -> Date
            LocalDateTime createdAt = student.getCreatedAt();
            Timestamp createdAtTimestamp = Timestamp.valueOf(createdAt);
            Date createdAtdate = new Date(createdAtTimestamp.getTime());
            pstmt.setDate(5, createdAtdate);

            int rs = pstmt.executeUpdate();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(String param_id){
        //todo#2 학생 조회
        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement("SELECT  * FROM jdbc_student WHERE id = ?")) {
            pstmt.setString(1, param_id);

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
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    int age = rs.getInt("age");

                    Student findStudent = new Student(id, name, Student.GENDER.valueOf(gender), age);
                    return Optional.of(findStudent);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 학생 수정 , name 수정
        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement("UPDATE jdbc_student SET name = ?, gender = ?, age = ? WHERE id = ?")) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender().toString());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getId());

            int rs = pstmt.executeUpdate();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id){
        //todo#4 학생 삭제
        try (PreparedStatement pstmt = DbUtils.getConnection().prepareStatement("DELETE FROM jdbc_student WHERE id = ?")) {
            pstmt.setString(1, id);

            int rs = pstmt.executeUpdate();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
