package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.common.Page;
import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        String sql = "INSERT INTO jdbc_student(id,name,gender,age) VALUES(?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender().toString());
            statement.setInt(4,student.getAge());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        String sql = "SELECT * FROM jdbc_student WHERE id=?";
        log.debug("findById:{}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1,id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            Student.GENDER.valueOf(rs.getString("gender")),
                            rs.getInt("age"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    return Optional.of(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        String sql = "UPDATE jdbc_students SET name=?, gender=?, age=? WHERE id=?";
        log.debug("update:{}",sql);

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            int index = 0;
            statement.setString(++index, student.getName());
            statement.setString(++index, student.getGender().toString());
            statement.setInt(++index, student.getAge());
            statement.setString(++index, student.getId());

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection,String id){
        String sql = "DELETE FROM jdbc_student WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll(Connection connection) {
        String sql = "DELETE FROM jdbc_student";

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long totalCount(Connection connection) {
        // todo#4 totalCount 구현
        String sql = "SELECT COUNT(*) FROM jdbc_student";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0l;
    }

    @Override
    public Page<Student> findAll(Connection connection, int page, int pageSize) {
        // todo#5 페이징 처리 구현
        int offset = (page - 1) * pageSize;
        int limit = pageSize;

        String sql = "SELECT id, name, gender, age, created_at FROM jdbc_student ORDER BY id DESC LIMIT  ?,? ";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1,offset);
            psmt.setInt(2,limit);

            try (ResultSet rs = psmt.executeQuery()) {
                List<Student> studentList = new ArrayList<>(pageSize);

                while (rs.next()) {
                    studentList.add(
                            new Student(
                                    rs.getString("id"),
                                    rs.getString("name"),
                                    Student.GENDER.valueOf(rs.getString("gender")),
                                    rs.getInt("age"),
                                    rs.getTimestamp("created_at").toLocalDateTime()
                            )
                    );
                }

                long total =0;
                if (!studentList.isEmpty()) {
                    // size>0 조회 시도, 0이면 조회할 필요 없음, count query는 자원을 많이 소모하는 작업
                    total = totalCount(connection);
                }

                return  new Page<Student>(studentList,total);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}