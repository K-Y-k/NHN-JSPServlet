package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.student.repository.impl.StatementStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class StatementStudentRepositoryTest {

    public static StudentRepository studentRepository;

    @BeforeAll
    static void setUp() throws SQLException {

        studentRepository = new StatementStudentRepository();

        Random random = new Random();
        Iterator<Integer> iterator = random.ints(20,50).iterator();
        for (int i = 1; i <= 10; i++) {
            String id="student" + i;
            String name="학생" + i;
            Student.GENDER gender = Student.GENDER.M;
            int age =iterator.next();

            Student student = new Student(id, name, gender, (Integer) age);
            studentRepository.deleteById(id);
            studentRepository.save(student);
        }

        studentRepository.deleteById("student100");
    }

    @Test
    @Order(1)
    @DisplayName("insert student : student 100")
    void save() throws SQLException {
        Student newStudent = new Student("student100","학생100", Student.GENDER.M,(Integer) 30);

        int result = studentRepository.save(newStudent);

        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("findById-student1")
    void findById() throws SQLException {
        Optional<Student> studentOptional = studentRepository.findById("student1");
        log.info("student:{}", studentOptional.get());

        Assertions.assertAll(
                ()->Assertions.assertEquals("student1",studentOptional.get().getId()),
                ()->Assertions.assertEquals("학생1",studentOptional.get().getName()),
                ()->Assertions.assertEquals(Student.GENDER.M,studentOptional.get().getGender())
        );
    }

    @Test
    @Order(3)
    @DisplayName("findById-marco10000")
    void findById_10000() throws SQLException {
        Optional<Student> studentOptional = studentRepository.findById("student10000");
        Assertions.assertFalse(studentOptional.isPresent());
    }

    @Test
    @Order(4)
    @DisplayName("update : student1")
    void update() throws SQLException {
        Student student = new Student("student1","엔에이치엔아카데미", Student.GENDER.F,(Integer) 30);

        int result = studentRepository.update(student);
        //Assume.assumeFalse(result>0);

        Optional<Student> newStudent = studentRepository.findById(student.getId());

        Assertions.assertAll(
                ()->Assertions.assertEquals("student1",newStudent.get().getId()),
                ()->Assertions.assertEquals("엔에이치엔아카데미",newStudent.get().getName()),
                ()->Assertions.assertEquals(Student.GENDER.F,newStudent.get().getGender()),
                ()->Assertions.assertEquals(30,newStudent.get().getAge())
        );
    }

    @Test
    @Order(5)
    @DisplayName("delete : student1")
    void deleteById() throws SQLException {
        String id="student1";
        int result = studentRepository.deleteById(id);
        Optional<Student> studentDto = studentRepository.findById(id);
        Assertions.assertFalse(studentDto.isPresent());
    }
}