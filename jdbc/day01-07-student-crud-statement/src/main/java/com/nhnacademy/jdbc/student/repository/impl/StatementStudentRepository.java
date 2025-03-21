package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student) {
        //todo#1 insert student
        String sql = "INSERT INTO jdbc_student VALUES ('" +
                student.getId() + "', '" + student.getName() + "', '" + student.getGender() + "', " +
                student.getAge() + ", '" + student.getCreatedAt()
                + "')";

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
    public Optional<Student> findById(String param_id) {
        //todo#2 student 조회
        String sql = "SELECT * FROM jdbc_student WHERE id = '" + param_id + "'";

        try (Statement stmt = DbUtils.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                /**
                 * 메소드 파라미터에 정보가 있지만 해당 위치를 모르는 사람들에게는 가독성이 떨어지므로
                 * db에서 가공할 때 다시 선언함
                 */
                String id = rs.getString("id");  // 컬럼 이름 사용
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                int age = rs.getInt("age");

                System.out.println("name: " + name + " gender: " + gender + " age: " + age);

                Student findStudent = new Student(id, name, Student.GENDER.valueOf(gender), (Integer) age);
                return Optional.of(findStudent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int update(Student student) {
        //todo#3 student 수정, name <- 수정합니다.
        String sql = "UPDATE jdbc_student " +
                "SET name='" + student.getName()
                + "', gender='" + student.getGender()
                + "', age=" + student.getAge();

        try (Statement stmt = DbUtils.getConnection().createStatement()) {
            int rs = stmt.executeUpdate(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id) {
       //todo#4 student 삭제
        String sql = "DELETE FROM jdbc_student WHERE id = '" + id + "'";

        try (Statement stmt = DbUtils.getConnection().createStatement()) {
            int rs = stmt.executeUpdate(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
